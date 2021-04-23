package com.example.mypetstore.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mypetstore.Repository.PetRepository;
import com.example.mypetstore.Room.Pet;

import java.util.HashMap;

public class AddEditViewModel extends AndroidViewModel {
    private PetRepository mPetRepository;

    public AddEditViewModel(@NonNull Application application) {
        super(application);
        mPetRepository = new PetRepository(application);
    }

    public void insert(Pet pet){
        mPetRepository.insert(pet);
    }
    public void delete(Pet pet){
        mPetRepository.delete(pet);
    }
    public void update(Pet pet){
        mPetRepository.update(pet);
    }

   public void insertOnline(HashMap<String , String> pet){
       mPetRepository.insertPetOnline(pet);
    }
}
