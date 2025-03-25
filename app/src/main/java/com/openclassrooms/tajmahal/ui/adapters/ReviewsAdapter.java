package com.openclassrooms.tajmahal.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.ReviewsBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

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
    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Constructeur du ViewHolder.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * Lie les données de l'avis au ViewHolder.
         *
         * @param review L'avis à afficher.
         */
        public void bind(Review review) {
            ReviewsBinding binding = ReviewsBinding.bind(itemView);

            // shows the name of the user
            binding.userName.setText(review.getUsername());

            // shows the rating of the user
            binding.userRating.setRating(review.getRate());

            // shows the comment of the user
            binding.userComment.setText(review.getComment());

            // shows the picture of the user
            Glide.with(itemView.getContext())
                    .load(review.getPicture())
                    .into(binding.userProfilePicture);
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

