package com.openclassrooms.tajmahal.ui.adapters;

import android.view.LayoutInflater;
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
 * An adapter for displaying the list of reviews in a RecyclerView.
 */
public class ReviewsAdapter extends ListAdapter<Review, ReviewsAdapter.ViewHolder> {

    /**
     * Adapter constructor.
     */
    public ReviewsAdapter() {
        super(new ItemCallback());
    }

    /**
     * ViewHolder constructor.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReviewsBinding binding = ReviewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    /**
     * Binds the data to the ViewHolder at the specified position.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    /**
     * ViewHolder to display review items.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
    private final ReviewsBinding binding;

        /**
         * ViewHolder constructor.
         */
        public ViewHolder(ReviewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds notification data to ViewHolder.
         *
         * @param review The notification to display.
         */
        public void bind(Review review) {
            binding.userName.setText(review.getUsername());
            binding.userRating.setRating(review.getRate());
            binding.userComment.setText(review.getComment());

            // Load the user profile picture
            Glide.with(binding.getRoot().getContext())
                    .load(review.getPicture())
                    .placeholder(R.drawable.img_default_user)
                    .error(R.drawable.img_default_user)
                    .into(binding.userProfilePicture);
        }
    }

    /**
     * Callback to compare list items.
     */
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

