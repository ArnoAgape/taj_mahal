package com.openclassrooms.tajmahal.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.domain.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * This is the repository class for managing restaurant data. Repositories are responsible
 * for coordinating data operations from data sources such as network APIs, databases, etc.
 * Typically in an Android app built with architecture components, the repository will handle
 * the logic for deciding whether to fetch data from a network source or use data from a local cache.
 *
 *
 * @see Restaurant
 * @see RestaurantApi
 */
@Singleton
public class RestaurantRepository {

    /** The API interface instance for fetching restaurant-related data. */
    private final RestaurantApi restaurantApi;

    /** LiveData object holding the list of reviews for real-time updates. */
    private final MutableLiveData<List<Review>> reviewsLiveData = new MutableLiveData<>(new ArrayList<>());

    /**
     * Constructs a new instance of {@link RestaurantRepository} with the given {@link RestaurantApi}.
     *
     * @param restaurantApi The network API interface for fetching restaurant data.
     */
    @Inject
    public RestaurantRepository(RestaurantApi restaurantApi) {
        this.restaurantApi = restaurantApi;
        fetchReviews();
    }

    /**
     * Retrieves the restaurant details.
     * <p>
     * This method fetches restaurant data via {@link RestaurantApi}.
     * Note that error handling and data transformations (if necessary) should be implemented
     * at a higher level, such as in the ViewModel.
     * </p>
     *
     * @return LiveData containing restaurant details.
     */
    public LiveData<Restaurant> getRestaurant() {
        return new MutableLiveData<>(restaurantApi.getRestaurant());
    }

    /**
     * Retrieves the list of reviews as LiveData.
     * <p>
     * The LiveData allows UI components to observe changes in real-time.
     * </p>
     *
     * @return LiveData containing a list of user reviews.
     */
    public LiveData<List<Review>> getReviews() {
        return reviewsLiveData;
    }

    /**
     * Retrieves the user details as LiveData.
     * <p>
     * This method provides user-related information fetched from the API.
     * </p>
     *
     * @return LiveData containing user details.
     */
    public LiveData<User> getUser() {
        return new MutableLiveData<>(restaurantApi.getUser());
    }

    /**
     * Fetches the list of reviews from the API.
     * <p>
     * This method makes a network call to fetch restaurant reviews and updates the LiveData object
     * so that observers (such as UI components) receive the latest data.
     * </p>
     */
    private void fetchReviews() {
        List<Review> reviews = restaurantApi.getReviews();
        reviewsLiveData.setValue(reviews);
    }

    /**
     * Adds a new review to the list.
     * <p>
     * This method updates the list of reviews and notifies any observers of the change.
     * The newly added review is placed at the top of the list.
     * </p>
     *
     * @param review The review to be added.
     */
    public void addReview(Review review) {
        List<Review> currentReviews = reviewsLiveData.getValue();
        if (currentReviews == null) {
            currentReviews = new ArrayList<>();
        }
        currentReviews.add(0, review); // Add new review at the beginning of the list
        reviewsLiveData.setValue(currentReviews); // Notify observers
    }
}