package com.example.cancer.data;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Write {

    private String email, name, info, image;

    public Write() { }

    public Write(String email, String name, String info, String image) {
        this.email = email;
        this.name = name;
        this.info = info;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getImage() {
        return image;
    }
}
