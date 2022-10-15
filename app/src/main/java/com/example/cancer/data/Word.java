package com.example.cancer.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.IgnoreExtraProperties;

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

    @ColumnInfo(name = "patient_name")
    String patientName;

    @ColumnInfo(name = "patient_surname")
    String patientSurname;

    @ColumnInfo(name = "patient_patronymic")
    String patientPatronymic;

    @ColumnInfo(name = "patient_phone")
    String patientPhone;

    @ColumnInfo(name = "day")
    String day;

    @ColumnInfo(name = "month")
    String month;

    @ColumnInfo(name = "year")
    String year;

    @Ignore
    public Word(@NonNull String name, @NonNull String info, String image, String patientName, String patientSurname, String patientPatronymic, String patientPhone, String day, String month, String year) {
        this.name = name;
        this.info = info;
        this.image = image;
        this.patientName = patientName;
        this.patientSurname = patientSurname;
        this.patientPatronymic = patientPatronymic;
        this.patientPhone = patientPhone;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Word(long id, @NonNull String name, @NonNull String info, String image, String patientName, String patientSurname, String patientPatronymic, String patientPhone, String day, String month, String year) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.image = image;
        this.patientName = patientName;
        this.patientSurname = patientSurname;
        this.patientPatronymic = patientPatronymic;
        this.patientPhone = patientPhone;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public long getId(){return this.id; }
}
