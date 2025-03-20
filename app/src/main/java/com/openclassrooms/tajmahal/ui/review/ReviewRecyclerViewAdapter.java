package com.openclassrooms.tajmahal.ui.review;

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

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.domain.model.Review;

/**
 * Un adaptateur pour afficher la liste des reviews dans un RecyclerView.
 */

public class ReviewRecyclerViewAdapter extends ListAdapter<Review, ReviewRecyclerViewAdapter.ViewHolder> {

    protected ReviewRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<Review> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_reviews, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));

    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView pictureProfileReview;
        private final TextView usernameReview;
        private final RatingBar rateReview;

        private final EditText commentReview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureProfileReview = itemView.findViewById(R.id.profile_picture);
            usernameReview = itemView.findViewById(R.id.name);
            rateReview = itemView.findViewById(R.id.rating);
            commentReview = itemView.findViewById(R.id.zone_texte);
        }

        public void bind(Review review) {

            // affiche le nom de la personne
            usernameReview.setText(review.getUsername());

            // affiche la photo de la personne
            pictureProfileReview.setText(review.getPicture());

            // affiche la note de la personne
            rateReview.setRating(review.getRate());

            // affiche le nom de la personne
            commentReview.setText(review.getComment());

        }
    }
}

