package com.example.cancer.models.cancer;

import com.google.gson.annotations.SerializedName;

public class Cancer {
    @SerializedName("s")
    public String res = "";

    public void setResult(String res) {
        this.res = res;
    }

    public String getResult() {
        return res;
    }
}
