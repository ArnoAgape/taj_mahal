package com.openclassrooms.tajmahal.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.domain.model.Review;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Un adaptateur pour afficher la liste des reviews dans un RecyclerView.
 */

public class ReviewsAdapter extends ListAdapter<Review, ReviewsAdapter.ViewHolder> {


    /**
     * Constructeur de l'adaptateur.
     */
    public ReviewsAdapter() {
        super(new ItemCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    /**
     * ViewHolder pour afficher les éléments de la liste des avis.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView usernameReview;
        private final ShapeableImageView pictureProfileReview;
        private final RatingBar rateReview;
        private final TextView commentReview;

        /**
         * Constructeur du ViewHolder.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pictureProfileReview = itemView.findViewById(R.id.userProfilePicture);
            usernameReview = itemView.findViewById(R.id.userName);
            rateReview = itemView.findViewById(R.id.userRating);
            commentReview = itemView.findViewById(R.id.userComment);
        }

        /**
         * Lie les données de l'avis au ViewHolder.
         *
         * @param review L'avis à afficher.
         */
        public void bind(Review review) {
            Log.d("ReviewsAdapter", "Binding review: " + review);
            if (review == null) {
                Log.e("ReviewsAdapter", "Review is null!");
                return; // Évitez de continuer si l'objet est nul
            }
            if (review == null) {
                usernameReview.setText("Erreur : avis non disponible");
                return;
            }
            // affiche le nom de la personne
            if (review.getUsername() != null) {
                usernameReview.setText(review.getUsername());
            } else {
                usernameReview.setText("Utilisateur inconnu");
            }

            // affiche la photo de la personne
            if (review.getPicture() != null && !review.getPicture().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(review.getPicture())
                        .into(pictureProfileReview);
            } else {
                pictureProfileReview.setImageResource(R.drawable.img_manon_garcia); // Image par défaut
            }
            Log.d("Glide", "URL de l'image : " + review.getPicture());


            // affiche la note de la personne
            rateReview.setRating(review.getRate());

            // affiche le nom de la personne
            if (review.getComment() != null) {
                commentReview.setText(review.getComment());
            } else {
                commentReview.setText("Pas de commentaire");
            }

        }
    }
    private static class ItemCallback extends DiffUtil.ItemCallback<Review> {

        @Override
        public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.getUsername().equals(newItem.getUsername());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.equals(newItem);
        }
    }
}

