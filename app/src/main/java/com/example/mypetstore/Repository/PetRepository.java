package com.example.mypetstore.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mypetstore.Network.Api.RetrofitInterface;
import com.example.mypetstore.Network.PetResponse;
import com.example.mypetstore.Network.RetrofitInstance;
import com.example.mypetstore.Room.Pet;
import com.example.mypetstore.Room.PetDaO;
import com.example.mypetstore.Room.PetDatabase;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetRepository {

    private PetDaO mPetDaO;
    private LiveData<List<Pet>> allPets;
    //private RetrofitInstance mRetrofitInstance;
    private RetrofitInterface mRetrofitInterface;
    private static final  String TAG = PetRepository.class.getSimpleName();

    public PetRepository(Application application){

        PetDatabase db  = PetDatabase.getInstance(application);
        mPetDaO = db.petDao();
        allPets = mPetDaO.getAllPets();

        mRetrofitInterface = RetrofitInstance.getRetrofitClient().create(RetrofitInterface.class);

    }

    public void update(Pet pet){
        new UpdatePetAsyncTask(mPetDaO).execute(pet);
    }
    public void delete(Pet pet){
        new DeletePetAsyncTask(mPetDaO).execute(pet);
    }
    public void insert(Pet pet){
        new InsertPetAsyncTask(mPetDaO).execute(pet);
    }
    public void deleteAllPets(){
        new DeleteAllPetAsyncTask(mPetDaO).execute();
    }

    public LiveData<List<Pet>> getAllPets(){
        return allPets;
    }



    public void insertPetOnline(HashMap <String,String> pet){

        mRetrofitInterface.addNewPet(pet).enqueue(new Callback<PetResponse>() {
            @Override
            public void onResponse(Call<PetResponse> call, Response<PetResponse> response) {
               if (response.code() == 200) {
                    Log.d(TAG, "onResponse: Pet added successfully ");
                } else if (response.code() == 400){
                  Log.d(TAG, "onResponse: Pet already registered");
                }
           }

            @Override
            public void onFailure(Call<PetResponse> call, Throwable t) {

               Log.d(TAG, "onFailure Error:  " + t.getMessage());

           }
       });


    }

    private static class UpdatePetAsyncTask extends AsyncTask<Pet , Void , Void> {

        private PetDaO petDao;
        private UpdatePetAsyncTask(PetDaO petDaO){
            this.petDao = petDaO;
        }
        @Override
        protected Void doInBackground(Pet... pets) {
            petDao.update(pets[0]);
            return null;
        }
    }

    private static class InsertPetAsyncTask extends AsyncTask<Pet , Void , Void> {

        private PetDaO petDao;
        private InsertPetAsyncTask(PetDaO petDaO){
            this.petDao = petDaO;
        }
        @Override
        protected Void doInBackground(Pet... pets) {
            petDao.insert(pets[0]);
            return null;
        }
    }

    private static class DeletePetAsyncTask extends AsyncTask<Pet , Void , Void> {

        private PetDaO petDao;
        private DeletePetAsyncTask(PetDaO petDaO){
            this.petDao = petDaO;
        }
        @Override
        protected Void doInBackground(Pet... pets) {
            petDao.delete(pets[0]);
            return null;
        }
    }

    private static class DeleteAllPetAsyncTask extends AsyncTask<Void, Void , Void> {

        private PetDaO petDao;
        private DeleteAllPetAsyncTask(PetDaO petDaO){
            this.petDao = petDaO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            petDao.deleteAllNotes();
            return null;
        }
    }

}
