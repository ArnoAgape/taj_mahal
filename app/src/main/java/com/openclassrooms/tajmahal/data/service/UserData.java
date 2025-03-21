package com.openclassrooms.tajmahal.data.service;

import com.openclassrooms.tajmahal.domain.model.User;

public class UserData {

    public User getUser() {
        return new User("Manon Garcia", "06.12.34.56.78", "manon.garcia@gmail.com",
                "https://xsgames.co/randomusers/assets/avatars/female/71.jpg", 3, "");
    }
}
