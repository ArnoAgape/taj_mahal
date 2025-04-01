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

    // The API interface instance that will be used for network requests related to restaurant data.
    private final RestaurantApi restaurantApi;
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
     * Fetches the restaurant details and the reviews
     * This method will make a network call using the provided {@link RestaurantApi} instance
     * to fetch restaurant data. Note that error handling and any transformations on the data
     * would need to be managed.
     *
     *
     * @return LiveData holding the restaurant details.
     */
    public LiveData<Restaurant> getRestaurant() {
        return new MutableLiveData<>(restaurantApi.getRestaurant());
    }

    public LiveData<List<Review>> getReviews() {
        return reviewsLiveData;
    }

    public LiveData<User> getUsers() {
        return new MutableLiveData<>(restaurantApi.getUsers());
    }

    private void fetchReviews() {
        List<Review> reviews = restaurantApi.getReviews();
        reviewsLiveData.setValue(reviews);
    }

    /**
     * Allows to add a new review.
     *
     */
    public void addReview(Review review) {
        List<Review> currentReviews = reviewsLiveData.getValue();
        if (currentReviews == null) {
            currentReviews = new ArrayList<>();
        }
        currentReviews.add(0, review); // Add new notice first
        // Notify the observer
        reviewsLiveData.setValue(currentReviews); // Create a new instance to trigger observation

    }
}
