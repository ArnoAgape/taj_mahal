package com.openclassrooms.tajmahal.ui.review;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.domain.model.User;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ReviewsViewModel is responsible for preparing and managing the data for the {@link ReviewsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and provides
 * utility methods related to the restaurant UI.
 * <p>
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class ReviewsViewModel extends ViewModel {
    private final RestaurantRepository restaurantRepository;

    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public ReviewsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Allows to add a new review.
     */
    public void addReview(String comment, int rating, User user) {
        Review newReview = new Review(user.getName(), user.getProfilePicture(), comment, rating);
        restaurantRepository.addReview(newReview);
    }

    /**
     * Data from the Restaurant to updateUIWithRestaurant
     */
    public LiveData<Restaurant> getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    /**
     * Fetches the details of the reviews.
     *
     * @return LiveData object containing the details of the reviews.
     */

    public LiveData<List<Review>> getReviews() {
        return restaurantRepository.getReviews();
    }

    /**
     * Users from the user database to updateUIWithUser
     */
    public LiveData<User> getUsers() {
        return restaurantRepository.getUsers();
    }

}
