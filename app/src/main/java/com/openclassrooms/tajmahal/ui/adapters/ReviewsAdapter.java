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

import java.util.ArrayList;
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
        Review review = getItem(position);
        holder.bind(getItem(position));
        Log.d("ReviewsAdapter", "Affichage de l'avis : " + review.getComment());
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

            // affiche le nom de la personne
            usernameReview.setText(review.getUsername());


            // affiche la photo de la personne

            Glide.with(itemView.getContext())
                    .load(review.getPicture())
                    .into(pictureProfileReview);


            // affiche la note de la personne
            rateReview.setRating(review.getRate());

            // affiche le nom de la personne

            commentReview.setText(review.getComment());

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

