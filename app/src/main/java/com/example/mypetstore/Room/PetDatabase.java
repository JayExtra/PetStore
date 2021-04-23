package com.example.mypetstore.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Pet.class , version=1)
public abstract class PetDatabase extends RoomDatabase {

    private static PetDatabase instance;

    public abstract PetDaO petDao();

    public static synchronized  PetDatabase getInstance(Context context){
        if(instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext() ,
                    PetDatabase.class , "pet_information")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
