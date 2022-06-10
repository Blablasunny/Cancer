package com.example.cancer.models.user;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String
            name,
            surname,
            patronymic,
            med,
            phone,
            email;

    public User() {
    }


    public User(String name, String surname, String patronymic, String med, String phone, String email) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.med = med;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getMed() {
        return med;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
