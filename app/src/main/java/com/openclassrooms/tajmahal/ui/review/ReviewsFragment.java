package com.openclassrooms.tajmahal.ui.review;


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
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
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
            binding.editText.getText().clear();
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
     * Checks if the button is enabled.
     */
    private void updateValidateButton() {
        int grey = Color.parseColor("#DADADA"); // Grey
        int red = Color.parseColor("#E91E63"); // Red
        String comment = String.valueOf(binding.editText.getText());
        int rating = (int) binding.rating.getRating();

        if (rating < 1 || comment.length() < 5) {
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

    public static ReviewsFragment newInstance() {
        return new ReviewsFragment();
    }
}
