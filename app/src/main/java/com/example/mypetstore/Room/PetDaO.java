package com.example.mypetstore.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PetDaO {

    @Insert
    void insert(Pet pet);

    @Update
    void update(Pet pet);

    @Delete
    void delete(Pet pet);

    @Query("DELETE FROM pet_info")
    void deleteAllNotes();

    @Query("SELECT * FROM pet_info ORDER BY petName ASC")
    LiveData<List<Pet>> getAllPets();

}
