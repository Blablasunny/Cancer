package com.example.cancer.data;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name, surname, patronymic, med, phone, email;

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
}
