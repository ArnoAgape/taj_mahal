package com.openclassrooms.tajmahal.ui.review;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.domain.model.User;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * MainViewModel is responsible for preparing and managing the data for the {@link DetailsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and provides
 * utility methods related to the restaurant UI.
 * <p>
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class ReviewsViewModel extends ViewModel {
    private final MutableLiveData<List<Review>> reviewsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final RestaurantRepository restaurantRepository;

    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public ReviewsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;

        // Observer les avis et mettre à jour reviewsLiveData
        restaurantRepository.getReviews().observeForever(reviews -> {
            if (reviews != null) {
                reviewsLiveData.setValue(reviews);
            } else {
                reviewsLiveData.setValue(new ArrayList<>());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        restaurantRepository.getReviews().removeObserver(reviewsLiveData::setValue);
    }

    public void addReview(String comment, int rating, User user) {
        List<Review> currentReviews = reviewsLiveData.getValue();
        if (currentReviews == null) {
            currentReviews = new ArrayList<>();
        }

        // Créer un nouvel avis et l'ajouter au début
        Review newReview = new Review(user.getName(), user.getProfilePicture(), comment, rating);
        currentReviews.add(0, newReview);

        // Notifier l'observateur
        reviewsLiveData.setValue(currentReviews); // Création d'une nouvelle instance pour déclencher l'observation
    }

    /**
     * Fetches the details of the reviews.
     *
     * @return LiveData object containing the details of the reviews.
     */
    public LiveData<List<Review>> getReviews() {
        Log.d("ReviewsViewModel", "Nombre de reviews récupérées : " + reviewsLiveData);
        return reviewsLiveData;

    }

}
