package com.example.cancer;

import com.google.gson.annotations.SerializedName;

public class Cancer1 {
    @SerializedName("s")
    public String res = "";

    public void setOrigin(String res) {
        this.res = res;
    }

    public String getResult() {
        return res;
    }
}
