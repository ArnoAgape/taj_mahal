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
     * Maps the reviews of the Taj Mahal restaurant.
     *
     * @return new DetailsReviewState containing the methods used in DetailsFragment.
     */
    LiveData<DetailsReviewState> getTajMahalReviews() {
        return Transformations.map(restaurantRepository.getReviews(), reviews -> {
            // Called everytime the value inside the LiveData of restaurantRepository.getReviews() is changed
            return new DetailsReviewState(
                    getAverageRating(),
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
        String dayString;

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                dayString = context.getString(R.string.monday);
                break;
            case Calendar.TUESDAY:
                dayString = context.getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                dayString = context.getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                dayString = context.getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                dayString = context.getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                dayString = context.getString(R.string.saturday);
                break;
            case Calendar.SUNDAY:
                dayString = context.getString(R.string.sunday);
                break;
            default:
                dayString = "";
        }
        return dayString;
    }

    /**
     * Calculates the average of the rates of the users
     */
    private double getAverageRating() {
        List<Review> reviews = restaurantRepository.getReviews().getValue();
        if (reviews == null || reviews.isEmpty()) {
            return 0;
        }
        double average = reviews.stream().mapToInt(Review::getRate).average().orElse(0);

        // Format average with 1 decimal place
        return Double.parseDouble(String.format(Locale.US, "%.1f", average));
    }

    /**
     * Shows the ProgressLinearBar according to the rates of the users
     */
    private int countingRate(int rate) {
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
        return (int) ((count / (double) reviews.size()) * 100);
    }
}
