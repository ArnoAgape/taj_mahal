package com.openclassrooms.tajmahal.ui.review;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.domain.model.User;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsFragment;

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
public class UserViewModel extends ViewModel {
    private final MutableLiveData<List<User>> userLiveData = new MutableLiveData<>();
    private final RestaurantRepository restaurantRepository;

    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public UserViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Fetches the details of the reviews.
     *
     * @return LiveData object containing the details of the reviews.
     */
    public LiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> users = restaurantRepository.getUsers();
        Log.d("UserViewModel", "Nombre d'utilisateur récupéré : " + (users != null ? Objects.requireNonNull(users.getValue()).size() : "null"));
        return restaurantRepository.getUsers();

    }


}
