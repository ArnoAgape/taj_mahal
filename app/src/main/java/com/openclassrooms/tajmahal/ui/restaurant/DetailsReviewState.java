package com.openclassrooms.tajmahal.ui.restaurant;

import java.util.Objects;

/**
 * Represents the state of reviews for a restaurant, including average rating,
 * total number of reviews, and the count of ratings from 1 to 5 stars.
 */
final class DetailsReviewState {

    private final double averageRating;
    private final int numberOfReviews;
    private final int countingRate1;
    private final int countingRate2;
    private final int countingRate3;
    private final int countingRate4;
    private final int countingRate5;

    /**
     * Constructs a new {@link DetailsReviewState} with the given review statistics.
     *
     * @param averageRating The average rating of all reviews.
     * @param numberOfReviews The total number of reviews.
     * @param countingRate1 The count of reviews with a 1-star rating.
     * @param countingRate2 The count of reviews with a 2-star rating.
     * @param countingRate3 The count of reviews with a 3-star rating.
     * @param countingRate4 The count of reviews with a 4-star rating.
     * @param countingRate5 The count of reviews with a 5-star rating.
     */
    DetailsReviewState(double averageRating, int numberOfReviews, int countingRate1, int countingRate2, int countingRate3, int countingRate4, int countingRate5) {
        this.averageRating = averageRating;
        this.numberOfReviews = numberOfReviews;
        this.countingRate1 = countingRate1;
        this.countingRate2 = countingRate2;
        this.countingRate3 = countingRate3;
        this.countingRate4 = countingRate4;
        this.countingRate5 = countingRate5;
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
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * Gets the count of 1-star reviews.
     *
     * @return The number of 1-star reviews.
     */
    public int getCountingRate1() {
        return countingRate1;
    }

    /**
     * Gets the count of 2-star reviews.
     *
     * @return The number of 2-star reviews.
     */
    public int getCountingRate2() {
        return countingRate2;
    }

    /**
     * Gets the count of 3-star reviews.
     *
     * @return The number of 3-star reviews.
     */
    public int getCountingRate3() {
        return countingRate3;
    }

    /**
     * Gets the count of 4-star reviews.
     *
     * @return The number of 4-star reviews.
     */
    public int getCountingRate4() {
        return countingRate4;
    }

    /**
     * Gets the count of 5-star reviews.
     *
     * @return The number of 5-star reviews.
     */
    public int getCountingRate5() {
        return countingRate5;
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
                && countingRate1 == that.countingRate1
                && countingRate2 == that.countingRate2
                && countingRate3 == that.countingRate3
                && countingRate4 == that.countingRate4
                && countingRate5 == that.countingRate5;
    }

    /**
     * Generates a hash code for this object.
     *
     * @return The hash code based on the review statistics.
     */
    @Override
    public int hashCode() {
        return Objects.hash(averageRating, numberOfReviews, countingRate1, countingRate2, countingRate3, countingRate4, countingRate5);
    }
}