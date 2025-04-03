package com.openclassrooms.tajmahal;

import org.junit.Test;

import static org.junit.Assert.*;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Review;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ReviewUnitTest {
    @Test
    public void testReviewIsAdded() throws Exception {
        // Create a new Review instance with a rating and a comment
        Review review = new Review("Patrick Bruel", "https://xsgames.co/randomusers/assets/avatars/male/91.jpg", "Service au top !", 5);
        // Check that the review actually contains this comment
        assertEquals(42, RestaurantRepo, 0.001);
    }
}