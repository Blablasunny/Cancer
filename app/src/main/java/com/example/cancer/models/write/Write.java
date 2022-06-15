package com.example.cancer.models.write;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Write {

    private String email, name, info, image;
    private long id;

    public Write() { }

    public Write(String email, String name, String info, String image, long id) {
        this.email = email;
        this.name = name;
        this.info = info;
        this.image = image;
        this.id = id;
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

    public long getId() { return id; }

    public String getEmail() { return email; }
}
