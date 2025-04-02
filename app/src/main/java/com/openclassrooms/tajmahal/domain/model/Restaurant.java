package com.openclassrooms.tajmahal.domain.model;


import java.util.Objects;

/**
 * Represents a restaurant and its various attributes.
 * <p>
 * This class models a restaurant with its name, type (e.g., Indian, Italian), operational hours,
 * address, website, phone number, and availability of dine-in and take-away options.
 * </p>
 * <p>
 * For beginners: A class is a blueprint for creating objects in Java. Objects are instances of a class.
 * In this case, the class 'Restaurant' allows us to represent different restaurants with their specific details.
 * <p>
 * Example:
 * <pre>
 * Restaurant tajMahal = new Restaurant("Taj Mahal", "Indian", "11h30 - 22h00",
 *                                      "123 Street", "http://tajmahal.com", "1234567890", true,
 *                                      false, 4.0, (257), true);
 * </pre>
 */
public class Restaurant {

    // Member variables representing attributes of a restaurant.
    private String name;
    private String type;
    private String hours;
    private String address;
    private String website;
    private String phoneNumber;
    private boolean dineIn;
    private boolean takeAway;
    private String rate;
    private String numberReviews;
    private Boolean allStars;

    /**
     * Constructor for the Restaurant class.
     *
     * @param name          The name of the restaurant.
     * @param type          The type or cuisine of the restaurant (e.g., Indian, Italian).
     * @param hours         The operational hours of the restaurant.
     * @param address       The address of the restaurant.
     * @param website       The website URL of the restaurant.
     * @param phoneNumber   The contact phone number of the restaurant.
     * @param dineIn        A boolean indicating if dine-in is available.
     * @param takeAway      A boolean indicating if take-away service is available.
     * @param rate          The rate of the restaurant.
     * @param numberReviews The number of reviews of the restaurant.
     * @param allStars      The percentage of reviews from 1 to 5 stars
     */
    public Restaurant(String name, String type, String hours, String address, String website, String phoneNumber, boolean dineIn, boolean takeAway, String rate, String numberReviews, boolean allStars) {
        this.name = name;
        this.type = type;
        this.hours = hours;
        this.address = address;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.dineIn = dineIn;
        this.takeAway = takeAway;
        this.rate = rate;
        this.numberReviews = numberReviews;
        this.allStars = allStars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return dineIn == that.dineIn && takeAway == that.takeAway && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(hours, that.hours) && Objects.equals(address, that.address) && Objects.equals(website, that.website) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(rate, that.rate) && Objects.equals(numberReviews, that.numberReviews) && Objects.equals(allStars, that.allStars);
    }

    /**
     * Calculates the hash code using the job description and due date.
     *
     * @return The calculated hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, type, hours, address, website, phoneNumber, dineIn, takeAway, rate, numberReviews, allStars);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isDineIn() {
        return dineIn;
    }

    public void setDineIn(boolean dineIn) {
        this.dineIn = dineIn;
    }

    public boolean isTakeAway() {
        return takeAway;
    }

    public void setTakeAway(boolean takeAway) {
        this.takeAway = takeAway;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getNumberReviews() {
        return numberReviews;
    }

    public void setNumberReviews(String numberReviews) {
        this.numberReviews = numberReviews;
    }

    public boolean displayAllStars() {
        return allStars;
    }
    public void setAllStars(boolean allStars) {
        this.allStars = allStars;
    }
}
