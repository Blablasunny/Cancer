package com.example.cancer.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cancer.data.Word;

import java.util.List;

@Dao
public interface WordDao {

    @Query("SELECT * FROM information")
    List<Word> loadAll();

    @Delete
    void delete(Word word);

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

    @Query("SELECT * FROM information where name = :name")
    List<Word> loadAllByName(String name);
}
