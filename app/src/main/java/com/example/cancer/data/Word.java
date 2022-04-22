package com.example.cancer.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;
@Entity(tableName = "information")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    String name;

    @NonNull
    @ColumnInfo(name = "info")
    String info;

    @ColumnInfo(name = "image")
    String image;

    public Word(String name, String info, String image){
        this.name = name;
        this.info = info;
        this.image = image;
    }

    public String getName(){
        return this.name;
    }
}
