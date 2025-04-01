package com.openclassrooms.tajmahal.ui.restaurant;

import java.util.Objects;

final class DetailsReviewState {
    private double averageRating;
    private int numberOfReviews;
    private int countingRate1;
    private int countingRate2;
    private int countingRate3;
    private int countingRate4;
    private int countingRate5;

    DetailsReviewState(double averageRating, int numberOfReviews, int countingRate1, int countingRate2, int countingRate3, int countingRate4, int countingRate5) {
        this.averageRating = averageRating;
        this.numberOfReviews = numberOfReviews;
        this.countingRate1 = countingRate1;
        this.countingRate2 = countingRate2;
        this.countingRate3 = countingRate3;
        this.countingRate4 = countingRate4;
        this.countingRate5 = countingRate5;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getCountingRate1() {
        return countingRate1;
    }

    public void setCountingRate1(int countingRate1) {
        this.countingRate1 = countingRate1;
    }

    public int getCountingRate2() {
        return countingRate2;
    }

    public void setCountingRate2(int countingRate2) {
        this.countingRate2 = countingRate2;
    }

    public int getCountingRate3() {
        return countingRate3;
    }

    public void setCountingRate3(int countingRate3) {
        this.countingRate3 = countingRate3;
    }

    public int getCountingRate4() {
        return countingRate4;
    }

    public void setCountingRate4(int countingRate4) {
        this.countingRate4 = countingRate4;
    }

    public int getCountingRate5() {
        return countingRate5;
    }

    public void setCountingRate5(int countingRate5) {
        this.countingRate5 = countingRate5;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DetailsReviewState that = (DetailsReviewState) o;
        return Double.compare(averageRating, that.averageRating) == 0 && numberOfReviews == that.numberOfReviews && countingRate1 == that.countingRate1 && countingRate2 == that.countingRate2 && countingRate3 == that.countingRate3 && countingRate4 == that.countingRate4 && countingRate5 == that.countingRate5;
    }

    @Override
    public int hashCode() {
        return Objects.hash(averageRating, numberOfReviews, countingRate1, countingRate2, countingRate3, countingRate4, countingRate5);
    }
}
