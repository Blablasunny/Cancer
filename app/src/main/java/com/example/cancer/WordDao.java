package com.example.cancer;

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Word... words);

    @Update
    void update(Word... words);

    @Query("SELECT image FROM information where name = :name")
    String getImageByName(String name);

    @Query("SELECT info FROM information where name = :name")
    String getInfoByName(String name);
}
