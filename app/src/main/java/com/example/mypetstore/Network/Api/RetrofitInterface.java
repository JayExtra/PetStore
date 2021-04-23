package com.example.mypetstore.Network.Api;

import com.example.mypetstore.Network.PetResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {

  @GET("/pets")
    Call<List<PetResponse>> getPets();

  @POST("/pets")
  Call<PetResponse> addNewPet(@Body HashMap<String , String> map);

}
