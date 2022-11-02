package com.example.cancer.data.words;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Query("SELECT * FROM information")
    List<Word> loadAll();

    @Delete
    void delete(Word word);

    @Query("DELETE from information")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Word word);

    @Update
    void update(Word word);

    @Query("SELECT image FROM information where id = :id")
    String getImageById(long id);

    @Query("SELECT info FROM information where id = :id")
    String getInfoById(long id);

    @Query("SELECT name FROM information where id = :id")
    String getNameById(long id);

    @Query("SELECT patient_name FROM information where id = :id")
    String getPatientNameById(long id);

    @Query("SELECT patient_surname FROM information where id = :id")
    String getPatientSurnameById(long id);

    @Query("SELECT patient_patronymic FROM information where id = :id")
    String getPatientPatronymicById(long id);

    @Query("SELECT patient_phone FROM information where id = :id")
    String getPatientPhoneById(long id);

    @Query("SELECT day FROM information where id = :id")
    String getDayById(long id);

    @Query("SELECT month FROM information where id = :id")
    String getMonthById(long id);

    @Query("SELECT year FROM information where id = :id")
    String getYearById(long id);

    @Query("SELECT * FROM information where name = :name")
    List<Word> loadAllByName(String name);

    @Query("SELECT * FROM information where day = :day AND month = :month AND year = :year")
    List<Word> loadAllByDate(String day, String month, String year);

    @Query("SELECT * FROM information where patient_surname = :patientSurname")
    List<Word> loadAllByPatientSurname(String patientSurname);


}
