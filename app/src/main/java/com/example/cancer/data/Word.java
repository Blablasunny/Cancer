package com.example.cancer.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "information")
public class Word {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    long id;

    @NonNull
    @ColumnInfo(name = "name")
    String name;

    @NonNull
    @ColumnInfo(name = "info")
    String info;

    @ColumnInfo(name = "image")
    String image;

    @Ignore
    public Word(String name, String info, String image){
        this.name = name;
        this.info = info;
        this.image = image;
    }

    public Word(long id, String name, String info, String image){
        this.name = name;
        this.info = info;
        this.image = image;
        this.id = id;
    }

    public long getId(){return this.id; }
}
