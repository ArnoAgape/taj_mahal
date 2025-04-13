package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import javax.inject.Inject;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * DetailsViewModel is responsible for preparing and managing the data for the {@link DetailsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and provides
 * utility methods related to the restaurant UI.
 * <p>
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class DetailsViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;

    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public DetailsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Fetches the details of the Taj Mahal restaurant.
     *
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */
    public LiveData<Restaurant> getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    /**
     * Returns a LiveData of {@link DetailsReviewState}, representing a structured and summarized view
     * of all user reviews for the Taj Mahal restaurant.
     *
     * <p>This method uses {@code Transformations.map} to observe the list of reviews from the
     * {@link RestaurantRepository}. Every time the list of reviews is updated, this transformation
     * is triggered to:
     * <ul>
     *     <li>Calculate the average rating.</li>
     *     <li>Count the total number of reviews.</li>
     *     <li>Count how many reviews have a rating from 1 to 5 stars.</li>
     * </ul>
     *
     * <p>This processed data is then wrapped in a {@link DetailsReviewState} object,
     * which can be directly observed by the View layer ({@code DetailsFragment}) to update the UI.
     *
     * @return LiveData containing the current state of restaurant reviews in a display-ready format.
     */

    public LiveData<DetailsReviewState> getTajMahalReviews() {
        return Transformations.map(restaurantRepository.getReviews(), reviews -> {
            // Called everytime the value inside the LiveData of restaurantRepository.getReviews() is changed
            return new DetailsReviewState(
                    (float) getAverageRating(),
                    reviews.size(),
                    countingRate(1),
                    countingRate(2),
                    countingRate(3),
                    countingRate(4),
                    countingRate(5)
            );
        });
    }

    /**
     * Retrieves the current day of the week in French.
     *
     * @return A string representing the current day of the week in French.
     */
    public String getCurrentDay(Context context) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        return switch (dayOfWeek) {
            case Calendar.MONDAY -> context.getString(R.string.monday);
            case Calendar.TUESDAY -> context.getString(R.string.tuesday);
            case Calendar.WEDNESDAY -> context.getString(R.string.wednesday);
            case Calendar.THURSDAY -> context.getString(R.string.thursday);
            case Calendar.FRIDAY -> context.getString(R.string.friday);
            case Calendar.SATURDAY -> context.getString(R.string.saturday);
            case Calendar.SUNDAY -> context.getString(R.string.sunday);
            default -> "";
        };
    }

    /**
     * Calculates the average of the rates of the users
     */
    public double getAverageRating() {
        List<Review> reviews = restaurantRepository.getReviews().getValue();
        if (reviews == null || reviews.isEmpty()) {
            return 0;
        }
        float average = (float) reviews.stream().mapToInt(Review::getRate).average().orElse(0);

        // Format average with 1 decimal place
        return Float.parseFloat(String.format(Locale.US, "%.1f", average));
    }

    /**
     * Shows the ProgressLinearBar according to the rates of the users
     */
    public double countingRate(double rate) {
        List<Review> reviews = restaurantRepository.getReviews().getValue();
        if (reviews == null || reviews.isEmpty()) {
            return 0; // avoid to divide by 0
        }
        int count = 0;
        for (Review review : reviews) {
            if (review.getRate() == rate) {
                count++;
            }
        }
        return (double) ((count / (double) reviews.size()) * 100);
    }
}
