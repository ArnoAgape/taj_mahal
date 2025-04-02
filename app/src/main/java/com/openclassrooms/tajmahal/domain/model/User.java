package com.openclassrooms.tajmahal.domain.model;

import java.util.Objects;

/**
 * Represents a user who leaves a review.
 * This class encapsulates all the details of a user, including their name, phone number,
 * email address, and profile picture.
 */
public class User {

    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String profilePicture;

    /**
     * Constructs a new {@link User} with the specified details.
     *
     * @param name           The name of the user.
     * @param phoneNumber    The contact phone number of the user.
     * @param emailAddress   The email address of the user.
     * @param profilePicture The URL of the user's profile picture.
     */
    public User(String name, String phoneNumber, String emailAddress, String profilePicture) {
        this.name = name;
        this.profilePicture = profilePicture;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the name of the user.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The new name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return The user's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber The new phone number of the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The user's email address.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address of the user.
     *
     * @param emailAddress The new email address of the user.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the profile picture URL of the user.
     *
     * @return The URL of the user's profile picture.
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     * Sets the profile picture URL of the user.
     *
     * @param profilePicture The new profile picture URL of the user.
     */
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * Compares this user to another object for equality.
     *
     * @param o The object to compare with.
     * @return {@code true} if both objects have the same user details, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(emailAddress, user.emailAddress) &&
                Objects.equals(profilePicture, user.profilePicture);
    }

    /**
     * Generates a hash code for this user.
     *
     * @return A hash code based on the user's details.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, emailAddress, profilePicture);
    }
}