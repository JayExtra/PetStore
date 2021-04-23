package com.example.mypetstore.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mypetstore.Repository.PetRepository;
import com.example.mypetstore.Room.Pet;

import java.util.List;

public class PetViewModel extends AndroidViewModel {
    private PetRepository mPetRepository;
    private LiveData<List<Pet>> allPets;
    public PetViewModel(@NonNull Application application) {
        super(application);
        mPetRepository = new PetRepository(application);
        allPets = mPetRepository.getAllPets();

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
    public void deleteAllPets(){
        mPetRepository.deleteAllPets();

    }
    public LiveData<List<Pet>> getAllPets(){
        return allPets;
    }
}
