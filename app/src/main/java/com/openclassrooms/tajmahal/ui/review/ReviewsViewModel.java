package com.openclassrooms.tajmahal.ui.review;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsFragment;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * MainViewModel is responsible for preparing and managing the data for the {@link DetailsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and provides
 * utility methods related to the restaurant UI.
 *
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
     * Fetches the details of the reviews.
     *
     * @return LiveData object containing the details of the reviews.
     */
    public LiveData<List<Review>> getReview() {
        LiveData<List<Review>> reviews = restaurantRepository.getReviews();
        Log.d("ReviewsViewModel", "Nombre de reviews récupérées : " + (reviews != null ? Objects.requireNonNull(reviews.getValue()).size() : "null"));
        return reviews != null ? reviews : new MutableLiveData<>(Collections.emptyList());

    }


}
