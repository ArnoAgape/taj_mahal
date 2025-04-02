package com.openclassrooms.tajmahal.ui.review;


import android.content.Context;
import android.content.res.ColorStateList;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.FragmentReviewsBinding;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.User;
import com.openclassrooms.tajmahal.ui.adapters.ReviewsAdapter;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsFragment;
import java.util.ArrayList;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * ReviewsFragment is the entry point of the reviews.
 * It displays reviews from users about a restaurant and provides functionality to submit a review:
 * rating, comment and user information.
 * <p>
 * This class uses {@link FragmentReviewsBinding} for data binding to its layout and
 * {@link ReviewsViewModel} to interact with data sources and manage UI-related data.
 */
@AndroidEntryPoint
public class ReviewsFragment extends Fragment {

    private ReviewsAdapter reviewsAdapter;
    private FragmentReviewsBinding binding;
    private ReviewsViewModel reviewsViewModel;

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

        recyclerViewSetup();
        buttonBack();
        updateValidateButton();
        updateListener();
        setupUI();
        setupViewModel();
        reviewsViewModel.getTajMahalRestaurant().observe(getViewLifecycleOwner(), this::updateUIWithRestaurant); // Observes changes in the restaurant data and updates the UI accordingly.
        reviewsViewModel.getUsers().observe(getViewLifecycleOwner(), this::updateUIWithUser); // Observes changes in the user data and updates the UI accordingly.
    }

    /**
     * Allows the reviews to be displayed thanks to the recyclerView.
     */
    private void recyclerViewSetup() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsAdapter = new ReviewsAdapter();
        binding.recyclerView.setAdapter(reviewsAdapter);
        reviewsViewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
        reviewsViewModel.getReviews().observe(getViewLifecycleOwner(), newReviews -> reviewsAdapter.submitList(new ArrayList<>(newReviews)));
    }

    /**
     * Submits a review
     * Updates the UI components with the provided user data.
     *
     * @param user The user object containing details to be displayed.
     */
    private void updateUIWithUser(User user) {
        if (user == null) return;

        binding.usernameUser.setText(user.getName()); // User name

        Glide.with(this) // User picture
                .load(user.getProfilePicture())
                .into(binding.profilePictureUser);

        binding.buttonValidate.setOnClickListener(v -> { // Button validate
            int rating = (int) binding.rating.getRating();
            String comment = String.valueOf(binding.editText.getText());
            reviewsViewModel.addReview(comment, rating, user);
            binding.rating.setRating(0);
            binding.editText.getText().clear();
            hideKeyboard();
            Toast.makeText(requireActivity(), R.string.review_submitted, Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Updates the UI components with the provided restaurant data.
     *
     * @param restaurant The restaurant object containing details to be displayed.
     */
    private void updateUIWithRestaurant(Restaurant restaurant) {
        if (restaurant == null) return;
        binding.tvRestaurantName.setText(restaurant.getName());
    }

    /**
     * Returns to the previous fragment.
     */
    private void buttonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DetailsFragment detailsFragment = DetailsFragment.newInstance();
            fragmentTransaction.replace(R.id.container, detailsFragment);
            fragmentTransaction.commit();
        });

        // back button from the phone
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        DetailsFragment detailsFragment = DetailsFragment.newInstance();
                        fragmentTransaction.replace(R.id.container, detailsFragment);
                        fragmentTransaction.commit();
                    }
                });
    }

    /**
     * Hides the Android keyboard after submitting a review
     */
    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Checks if the button is enabled.
     */
    private void updateValidateButton() {
        int grey = Color.parseColor("#DADADA"); // Grey
        int red = Color.parseColor("#E91E63"); // Red
        String comment = String.valueOf(binding.editText.getText());
        int rating = (int) binding.rating.getRating();
        String trimmedComment = comment.replaceAll("\\s+", ""); // Deletes all the spaces

        // conditions to submit a review
        if (rating < 1 || trimmedComment.length() < 5 || comment.length() > 1500) {
            binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(grey));
            binding.buttonValidate.setChipStrokeColor(ColorStateList.valueOf(grey));
            binding.buttonValidate.setEnabled(false);
        } else {
            binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(red));
            binding.buttonValidate.setChipStrokeColor(ColorStateList.valueOf(red));
            binding.buttonValidate.setEnabled(true);
        }
    }

    /**
     * Method to listen to the rating bar and the comment from the user.
     */
    private void updateListener() {
        // Listener for the RatingBar
        binding.rating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> updateValidateButton());

        // Listener for the EditText
        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateValidateButton(); // updates when the user is typing
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
     * Defines a static method for creating a new instance of ReviewsFragment.
     */
    public static ReviewsFragment newInstance() {
        return new ReviewsFragment();
    }
}
