package com.example.cancer.models.write;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Write {

    private String email, name, info, image, patientName, patientSurname, patientPatronymic, patientPhone, day, month, year;
    private long id;

    public Write() { }

    public Write(String email, String name, String info, String image, String patientName, String patientSurname, String patientPatronymic, String patientPhone, String day, String month, String year, long id) {
        this.email = email;
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

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientSurname() {
        return patientSurname;
    }

    public String getPatientPatronymic() {
        return patientPatronymic;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
