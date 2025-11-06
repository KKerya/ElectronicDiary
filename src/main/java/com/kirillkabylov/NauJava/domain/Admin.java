package com.kirillkabylov.NauJava.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_admins")
public class Admin extends UserEntity {
    public Admin() {
    }
    public Admin(String login, String fullName, String password) {
        super(login, fullName, password);
    }
}