package com.example.cancer.data;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Write {

    private String name, info, image;

    public Write() { }

    public Write(String name, String info, String image) {
        this.name = name;
        this.info = info;
        this.image = image;
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
