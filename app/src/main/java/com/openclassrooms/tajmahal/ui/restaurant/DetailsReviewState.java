package com.openclassrooms.tajmahal.ui.restaurant;

import java.util.Objects;

/**
 * Represents the state of reviews for a restaurant, including average rating,
 * total number of reviews, and the count of ratings from 1 to 5 stars.
 */
final class DetailsReviewState {

    private final float averageRating;
    private final int numberOfReviews;
    private final int progressBar1;
    private final int progressBar2;
    private final int progressBar3;
    private final int progressBar4;
    private final int progressBar5;

    /**
     * Constructs a new {@link DetailsReviewState} with the given review statistics.
     *
     * @param averageRating The average rating of all reviews.
     * @param numberOfReviews The total number of reviews.
     * @param progressBar1 The count of reviews with a 1-star rating.
     * @param countingRate2 The count of reviews with a 2-star rating.
     * @param countingRate3 The count of reviews with a 3-star rating.
     * @param countingRate4 The count of reviews with a 4-star rating.
     * @param countingRate5 The count of reviews with a 5-star rating.
     */
    DetailsReviewState(float averageRating, int numberOfReviews, int progressBar1, int countingRate2, int countingRate3, int countingRate4, int countingRate5) {
        this.averageRating = averageRating;
        this.numberOfReviews = numberOfReviews;
        this.progressBar1 = progressBar1;
        this.progressBar2 = countingRate2;
        this.progressBar3 = countingRate3;
        this.progressBar4 = countingRate4;
        this.progressBar5 = countingRate5;
    }

    /**
     * Gets the total number of reviews.
     *
     * @return The number of reviews.
     */
    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    /**
     * Gets the average rating of all reviews.
     *
     * @return The average rating.
     */
    public float getAverageRating() {
        return averageRating;
    }

    /**
     * Gets the count of 1-star reviews.
     *
     * @return The number of 1-star reviews.
     */
    public int getProgressBar1() {
        return progressBar1;
    }

    /**
     * Gets the count of 2-star reviews.
     *
     * @return The number of 2-star reviews.
     */
    public int getProgressBar2() {
        return progressBar2;
    }

    /**
     * Gets the count of 3-star reviews.
     *
     * @return The number of 3-star reviews.
     */
    public int getProgressBar3() {
        return progressBar3;
    }

    /**
     * Gets the count of 4-star reviews.
     *
     * @return The number of 4-star reviews.
     */
    public int getProgressBar4() {
        return progressBar4;
    }

    /**
     * Gets the count of 5-star reviews.
     *
     * @return The number of 5-star reviews.
     */
    public int getProgressBar5() {
        return progressBar5;
    }

    /**
     * Compares this object with another for equality.
     *
     * @param o The object to compare with.
     * @return {@code true} if both objects have the same review statistics, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DetailsReviewState that = (DetailsReviewState) o;
        return Double.compare(averageRating, that.averageRating) == 0
                && numberOfReviews == that.numberOfReviews
                && progressBar1 == that.progressBar1
                && progressBar2 == that.progressBar2
                && progressBar3 == that.progressBar3
                && progressBar4 == that.progressBar4
                && progressBar5 == that.progressBar5;
    }

    /**
     * Generates a hash code for this object.
     *
     * @return The hash code based on the review statistics.
     */
    @Override
    public int hashCode() {
        return Objects.hash(averageRating, numberOfReviews, progressBar1, progressBar2, progressBar3, progressBar4, progressBar5);
    }
}