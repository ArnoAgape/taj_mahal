package com.openclassrooms.tajmahal.domain.model;

import java.util.Objects;

/**
 * Represents Manon Garcia review.
 * This class encapsulates all the details of a review, including the username, her phone number,
 * her email address, her profile picture, the comment she left, and the rating she gave.
 */
public class User {

    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String profilePicture;

    /**
     * Constructor for the User class.
     *
     * @param name           The name of the user.
     * @param phoneNumber    The contact phone number of the user.
     * @param emailAddress   The email address of the user.
     * @param profilePicture The profile picture of the user.
     */
    public User(String name, String phoneNumber, String emailAddress, String profilePicture) {
        this.name = name;
        this.profilePicture = profilePicture;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(emailAddress, user.emailAddress) && Objects.equals(profilePicture, user.profilePicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, emailAddress, profilePicture);
    }
}
