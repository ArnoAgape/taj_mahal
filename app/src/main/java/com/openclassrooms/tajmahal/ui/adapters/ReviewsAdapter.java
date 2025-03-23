package com.openclassrooms.tajmahal.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.domain.model.User;

import java.util.Calendar;
import java.util.Date;

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

        private final LinearProgressIndicator ratingFive;
        private final LinearProgressIndicator ratingFour;
        private final LinearProgressIndicator ratingThree;
        private final LinearProgressIndicator ratingTwo;
        private final LinearProgressIndicator ratingOne;

        /**
         * Constructeur du ViewHolder.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pictureProfileReview = itemView.findViewById(R.id.userProfilePicture);
            usernameReview = itemView.findViewById(R.id.userName);
            rateReview = itemView.findViewById(R.id.userRating);
            commentReview = itemView.findViewById(R.id.userComment);
            ratingFive = itemView.findViewById(R.id.five_stars);
            ratingFour = itemView.findViewById(R.id.four_stars);
            ratingThree = itemView.findViewById(R.id.three_stars);
            ratingTwo = itemView.findViewById(R.id.two_stars);
            ratingOne = itemView.findViewById(R.id.one_star);
        }

        /**
         * Lie les données de l'avis au ViewHolder.
         *
         * @param review L'avis à afficher.
         */
        public void bind(Review review) {

            // affiche le nom de la personne qui a laissé un avis
            usernameReview.setText(review.getUsername());

            // affiche la photo de la personne
            Glide.with(itemView.getContext())
                    .load(review.getPicture())
                    .into(pictureProfileReview);


            // affiche la note de la personne
            rateReview.setRating(review.getRate());

            // affiche le nom de la personne
            commentReview.setText(review.getComment());
/*
            // affiche le nombre d'avis de 5 étoiles
            int pourcentage = calculateProgress(review.getRate());
            ratingFive.setProgress(pourcentage);

            // affiche le nombre d'avis de 4 étoiles
            ratingFour.setProgress(pourcentage);

            // affiche le nombre d'avis de 3 étoiles
            ratingThree.setProgress(pourcentage);

            // affiche le nombre d'avis de 2 étoiles
            ratingTwo.setProgress(pourcentage);

            // affiche le nombre d'avis de 1 étoile
            ratingOne.setProgress(pourcentage);
*/
        }

        /**
         * Fonction pour convertir le nombre d'étoiles en pourcentage
         */
        private int calculateProgress(Date dueTime) {
            Calendar dateDuJour = Calendar.getInstance();
            dateDuJour.set(Calendar.HOUR_OF_DAY, 0);
            dateDuJour.set(Calendar.MINUTE, 0);
            dateDuJour.set(Calendar.SECOND, 0);
            dateDuJour.set(Calendar.MILLISECOND, 0);

            Calendar dateTache = Calendar.getInstance();
            dateTache.setTime(dueTime);
            dateTache.set(Calendar.HOUR_OF_DAY, 0);
            dateTache.set(Calendar.MINUTE, 0);
            dateTache.set(Calendar.SECOND, 0);
            dateTache.set(Calendar.MILLISECOND, 0);

            int joursRestants = (int) ((dateTache.getTimeInMillis() -
                    dateDuJour.getTimeInMillis()) / (24 * 3600 * 1000));
            return 100 - (joursRestants * 10);


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

