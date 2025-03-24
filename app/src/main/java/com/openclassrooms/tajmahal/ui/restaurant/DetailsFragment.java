package com.openclassrooms.tajmahal.ui.restaurant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.databinding.FragmentDetailsBinding;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.review.ReviewsFragment;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * DetailsFragment is the entry point of the application and serves as the primary UI.
 * It displays details about a restaurant and provides functionality to open its location
 * in a map, call its phone number, or view its website.
 * <p>
 * This class uses {@link FragmentDetailsBinding} for data binding to its layout and
 * {@link DetailsViewModel} to interact with data sources and manage UI-related data.
 */
@AndroidEntryPoint
public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;

    private DetailsViewModel detailsViewModel;

    private final RestaurantFakeApi restaurantFakeApi = new RestaurantFakeApi();

    public DetailsFragment() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false); // Binds the layout using view binding.
        // Returns the root view.
        Log.d("FragmentCheck", "DetailsFragment onCreateView est affiché");
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
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("FragmentCheck", "DetailsFragment onViewCreated est affiché");

        List<Review> reviews = restaurantFakeApi.getReviews();
        int reviewsNumber = reviews.size();
        binding.reviewNumber.setText("(" + (reviewsNumber) + ")");

        Log.d("FragmentCheck", "Moyenne des avis : " + getAverageRating());
        binding.reviewRate.setText(String.valueOf(getAverageRating()));

        int percentage5 = countingRate5(reviews);
        int percentage4 = countingRate4(reviews);
        int percentage3 = countingRate3(reviews);
        int percentage2 = countingRate2(reviews);
        int percentage1 = countingRate1(reviews);
        binding.fiveStars.setProgress(percentage5);
        binding.fourStars.setProgress(percentage4);
        binding.threeStars.setProgress(percentage3);
        binding.twoStars.setProgress(percentage2);
        binding.oneStar.setProgress(percentage1);

        binding.reviewWrite.setOnClickListener(v -> {
            // navigate to ReviewsFragment
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ReviewsFragment reviewsFragment = ReviewsFragment.newInstance();
            fragmentTransaction.replace(R.id.container, reviewsFragment);
            fragmentTransaction.commit();
        });
        starRating();
        setupUI(); // Sets up user interface components.
        setupViewModel(); // Prepares the ViewModel for the fragment.
        detailsViewModel.getTajMahalRestaurant().observe(requireActivity(), this::updateUIWithRestaurant); // Observes changes in the restaurant data and updates the UI accordingly.
    }

    /**
     * Calculate the average of the rates of the users
     */
    public double getAverageRating() {
        List<Review> reviews = restaurantFakeApi.getReviews();
        return reviews.isEmpty() ? 0 :
                (double) reviews.stream().mapToInt(Review::getRate).average().orElse(0);
    }

    /**
     * Shows the rating stars according to the average of the rates of the users
     */
    public void starRating() {
        double averageRating = getAverageRating();

        // Cacher toutes les étoiles au départ
        binding.rating1.setVisibility(View.GONE);
        binding.rating2.setVisibility(View.GONE);
        binding.rating3.setVisibility(View.GONE);
        binding.rating4.setVisibility(View.GONE);
        binding.rating5.setVisibility(View.GONE);

        if (averageRating == 1.0)
            binding.rating1.setVisibility(View.VISIBLE);
        else if (averageRating > 1.0 && averageRating <= 2.0)
            binding.rating2.setVisibility(View.VISIBLE);
        else if (averageRating > 2.0 && averageRating <= 3.0)
            binding.rating3.setVisibility(View.VISIBLE);
        else if (averageRating > 3.0 && averageRating <= 4.0)
            binding.rating4.setVisibility(View.VISIBLE);
        else if (averageRating > 4.0 && averageRating < 5.0)
            binding.rating5.setVisibility(View.VISIBLE);
    }

    private int countingRate5(List<Review> reviews) {
        int count = 0;
        for (Review review : reviews) {
            if (review.getRate() == 5) {
                count++;
            }
        }
        return (int) ((count / (double) reviews.size()) * 100);
    }


    private int countingRate4(List<Review> reviews) {
        int count = 0;
        for (Review review : reviews) {
            if (review.getRate() == 4) {
                count++;
            }
        }
        return (int) ((count / (double) reviews.size()) * 100);
    }

    private int countingRate3(List<Review> reviews) {
        int count = 0;
        for (Review review : reviews) {
            if (review.getRate() == 3) {
                count++;
            }
        }
        return (int) ((count / (double) reviews.size()) * 100);
    }

    private int countingRate2(List<Review> reviews) {
        int count = 0;
        for (Review review : reviews) {
            if (review.getRate() == 2) {
                count++;
            }
        }
        return (int) ((count / (double) reviews.size()) * 100);
    }

    private int countingRate1(List<Review> reviews) {
        int count = 0;
        for (Review review : reviews) {
            if (review.getRate() == 1) {
                count++;
            }
        }
        return (int) ((count / (double) reviews.size()) * 100);
    }

    /**
     * Sets up the UI-specific properties, such as system UI flags and status bar color.
     */
    private void setupUI() {
        Window window = requireActivity().getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * Initializes the ViewModel for this activity.
     */
    private void setupViewModel() {
        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
    }

    /**
     * Updates the UI components with the provided restaurant data.
     *
     * @param restaurant The restaurant object containing details to be displayed.
     */
    private void updateUIWithRestaurant(Restaurant restaurant) {
        if (restaurant == null) return;

        binding.tvRestaurantName.setText(restaurant.getName());
        binding.tvRestaurantDay.setText(detailsViewModel.getCurrentDay(requireContext()));
        binding.tvRestaurantType.setText(String.format("%s %s", getString(R.string.restaurant), restaurant.getType()));
        binding.tvRestaurantHours.setText(restaurant.getHours());
        binding.tvRestaurantAddress.setText(restaurant.getAddress());
        binding.tvRestaurantWebsite.setText(restaurant.getWebsite());
        binding.tvRestaurantPhoneNumber.setText(restaurant.getPhoneNumber());
        binding.chipOnPremise.setVisibility(restaurant.isDineIn() ? View.VISIBLE : View.GONE);
        binding.chipTakeAway.setVisibility(restaurant.isTakeAway() ? View.VISIBLE : View.GONE);

        binding.buttonAddress.setOnClickListener(v -> openMap(restaurant.getAddress()));
        binding.buttonPhone.setOnClickListener(v -> dialPhoneNumber(restaurant.getPhoneNumber()));
        binding.buttonWebsite.setOnClickListener(v -> openBrowser(restaurant.getWebsite()));
        binding.allStars.setVisibility(restaurant.displayAllStars() ? View.VISIBLE : View.GONE);
    }

    /**
     * Opens the provided address in Google Maps or shows an error if Google Maps
     * is not installed.
     *
     * @param address The address to be shown in Google Maps.
     */
    private void openMap(String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(requireActivity(), R.string.maps_not_installed, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Dials the provided phone number or shows an error if there's no dialing application
     * installed.
     *
     * @param phoneNumber The phone number to be dialed.
     */
    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(requireActivity(), R.string.phone_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Opens the provided website URL in a browser or shows an error if there's no
     * browser installed.
     *
     * @param websiteUrl The URL of the website to be opened.
     */
    private void openBrowser(String websiteUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(requireActivity(), R.string.no_browser_found, Toast.LENGTH_SHORT).show();
        }
    }

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

}