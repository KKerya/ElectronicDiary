package com.kirillkabylov.NauJava.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends UserEntity {
    public Admin() {
    }

    public Admin(String login, String fullName, String password) {
        super(login, fullName, password);
    }
}