package com.openclassrooms.tajmahal.ui.review;


import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.databinding.FragmentReviewsBinding;
import com.openclassrooms.tajmahal.domain.model.User;
import com.openclassrooms.tajmahal.ui.adapters.ReviewsAdapter;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsFragment;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class ReviewsFragment extends Fragment {

    private ReviewsAdapter reviewsAdapter;
    private ReviewsViewModel reviewsViewModel;
    private FragmentReviewsBinding binding;
    private final RestaurantFakeApi restaurantFakeApi = new RestaurantFakeApi();

    private EditText editText;
    private Button buttonValidate;
    private TextView textViewResult;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * This method is called when the fragment is first created.
     * It's used to perform one-time initialization.
     *
     * @param savedInstanceState A bundle containing previously saved instance state.
     *                           If the fragment is being re-created from a previous saved state, this is the state.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     *                           The fragment should not add the view itself but return it.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Returns the View for the fragment's UI, or null.
     */

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * This method is called immediately after `onCreateView()`.
     * Use this method to perform final initialization once the fragment views have been inflated.
     *
     * @param view               The View returned by `onCreateView()`.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // shows the name of the restaurant
        binding.tvRestaurantName.setText(R.string.app_name);

        // shows the name of the user
        List<User> users = restaurantFakeApi.getUsers();
        User userName = users.get(0);
        binding.usernameUser.setText(userName.getName());

        // shows the profile picture of the user
        User profilePictureUser = users.get(0);
        Glide.with(this)
                .load(profilePictureUser.getProfilePicture())
                .into(binding.profilePictureUser);

        // when click on buttonBack, goes to the homepage
        binding.buttonBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DetailsFragment detailsFragment = DetailsFragment.newInstance();
            fragmentTransaction.replace(R.id.container, detailsFragment);
            fragmentTransaction.commit();
        });

        // when click on back button on phone, goes to the homepage
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        if (fragmentManager.getBackStackEntryCount() > 0) {
                            fragmentManager.popBackStack(); // Revenir au fragment précédent
                        } else
                            requireActivity().finish(); // Quitter l'application si aucun fragment précédent
                    }
                });

        // RecyclerView setup
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsAdapter = new ReviewsAdapter();
        binding.recyclerView.setAdapter(reviewsAdapter);
        reviewsViewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
        reviewsViewModel.getReviews().observe(getViewLifecycleOwner(), newReviews -> {reviewsAdapter.submitList(new ArrayList<>(newReviews));
        });

        binding.buttonValidate.setOnClickListener(v -> {
            /*binding.editText.setText();
            String enteredText = editText.getText().toString().trim();*/
            User currentUser = new User("Arno Bouiron", "0616161616", "test@gmail.com", "https://www.pixenli.com/image/nIujl02T");
            reviewsViewModel.addReview("super !!", 5, currentUser);
            /*FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ReviewsFragment reviewsFragment = ReviewsFragment.newInstance();
            fragmentTransaction.replace(R.id.container, reviewsFragment);
            fragmentTransaction.commit();*/
        });

        setupUI(); // Sets up user interface components.

        setupViewModel(); // Prepares the ViewModel for the fragment.

    }


    /**
     * Sets up the UI-specific properties, such as system UI flags and status bar color.
     */
    private void setupUI() {
        Window window = requireActivity().getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * Initializes the ViewModel for this activity.
     */
    private void setupViewModel() {
        reviewsViewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
    }

    /**
     * Updates the UI components with the provided restaurant data.
     *
     * @param review The restaurant object containing details to be displayed.
     */

    public static ReviewsFragment newInstance() {
        return new ReviewsFragment();
    }
}
