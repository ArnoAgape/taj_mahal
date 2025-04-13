package com.openclassrooms.tajmahal;

import org.junit.Test;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import org.junit.Rule;
import static org.junit.Assert.*;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.domain.model.User;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsReviewState;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsViewModel;
import com.openclassrooms.tajmahal.ui.review.ReviewsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ReviewUnitTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    /**
     * Verifies that a review's rating is correctly stored upon creation.
     */
    @Test
    public void testReviewIsAdded() {
        // Create a new Review instance with a rating and a comment
        Review review = new Review("Patrick Bruel", "https://xsgames.co/randomusers/assets/avatars/male/91.jpg", "Service au top !", 5);
        // Check that the review actually contains this comment
        assertEquals(5, review.getRate(), 0.001);
    }

    /**
     * Calculates the average of the reviews
     */

    @Test
    public void testAverageRating_And_ProgressBar() {

        User alice = new User("Alice", "0101010101", "alice@email.com", "url");
        User bob = new User("Bob", "0202020202", "bob@email.com", "url");
        User nicolas = new User("Nicolas", "0101010101", "alice@email.com", "url");

        RestaurantRepository repo = new RestaurantRepository(new RestaurantFakeApi());
        DetailsViewModel detailsViewModel = new DetailsViewModel(repo);
        ReviewsViewModel reviewsViewModel = new ReviewsViewModel(repo);
        reviewsViewModel.addReview("Au top !", 5, alice);
        reviewsViewModel.addReview("Passable", 1, bob);
        reviewsViewModel.addReview("Moyen !", 3, nicolas);
        List<Review> reviews = reviewsViewModel.getReviews().getValue();
        assert reviews != null;
        DetailsReviewState state = new DetailsReviewState((float) detailsViewModel.getAverageRating(), reviews.size(), detailsViewModel.countingRate(1),
                detailsViewModel.countingRate(2), detailsViewModel.countingRate(3), detailsViewModel.countingRate(4) , detailsViewModel.countingRate(5));

        assertEquals(3.6, state.getAverageRating(), 0.0001);
        assertEquals(12.5, state.getProgressBar1(), 0.0001);

    }

    /**
     * Verifies that two Review objects with identical content are considered equal.
     */
    @Test
    public void testReview_EqualsMethod_ShouldReturnTrue_WhenSameContent() {
        Review r1 = new Review("Manon", "url", "Très bon", 5);
        Review r2 = new Review("Manon", "url", "Très bon", 5);
        assertEquals(r1, r2);
    }

    /**
     * Tests the getter and setter methods of the User class.
     */
    @Test
    public void testUser_GettersAndSetters() {
        User user = new User("Manon", "0600000000", "manon@email.com", "url");
        assertEquals("Manon", user.getName());
        user.setName("Alice");
        assertEquals("Alice", user.getName());
    }

    /**
     * Ensures that an invalid review (blank comment and zero rating) is not added to the list.
     */
    @Test
    public void testAddInvalidReview_ShouldNotBeAdded() {
            RestaurantRepository repo = new RestaurantRepository(new RestaurantFakeApi());
            Objects.requireNonNull(repo.getReviews().getValue()).clear();
            ReviewsViewModel viewModel = new ReviewsViewModel(repo);

            User user = new User("Bob", "0606060606", "bob@email.com", "url");

            viewModel.addReview("      ", 0, user);

            List<Review> reviews = viewModel.getReviews().getValue();

            assertTrue(reviews == null || reviews.isEmpty());
    }

    /**
     * Verifies that a valid review is correctly added and reflected in the LiveData.
     */
    @Test
    public void testAddReview_ShouldReflectInLiveData() {
            RestaurantRepository repo = new RestaurantRepository(new RestaurantFakeApi());
            ReviewsViewModel viewModel = new ReviewsViewModel(repo);
            User user = new User("Bob", "0606060606", "bob@email.com", "url");

            viewModel.addReview("Great restaurant", 4, user);
            List<Review> reviews = viewModel.getReviews().getValue();

            assertNotNull(reviews);
            assertEquals(6, reviews.size());
            assertEquals("Bob", reviews.get(0).getUsername());
        }

    /**
     * Ensures that multiple reviews are inserted in the correct order
     * (the latest review should appear first).
     */
    @Test
    public void testAddMultipleReviews_ShouldMaintainOrder() {
        RestaurantRepository repo = new RestaurantRepository(new RestaurantFakeApi());
        Objects.requireNonNull(repo.getReviews().getValue()).clear();
        ReviewsViewModel viewModel = new ReviewsViewModel(repo);

        User alice = new User("Alice", "0101010101", "alice@email.com", "url");
        User bob = new User("Bob", "0202020202", "bob@email.com", "url");

        viewModel.addReview("Super !", 5, alice);
        viewModel.addReview("Excellent service", 4, bob);

        List<Review> reviews = viewModel.getReviews().getValue();

        assert reviews != null;
        assertEquals("Bob", reviews.get(0).getUsername());
        assertEquals("Alice", reviews.get(1).getUsername());
    }
}