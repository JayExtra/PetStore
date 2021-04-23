package com.example.mypetstore.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static String BASE_URL = "http://10.0.2.2:3000";
    private static Retrofit sRetrofit;

    public static Retrofit getRetrofitClient(){

        if(sRetrofit == null){

            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}
