package com.example.cancer.client;

import com.google.gson.annotations.SerializedName;

public class Cancer1 {
    @SerializedName("s")
    public String res = "";

    public void setResult(String res) {
        this.res = res;
    }

    public String getResult() {
        return res;
    }
}
