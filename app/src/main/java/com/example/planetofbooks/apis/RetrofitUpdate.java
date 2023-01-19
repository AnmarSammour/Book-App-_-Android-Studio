package com.example.planetofbooks.apis;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUpdate{

    private static RetrofitUpdate instance = null;
    private APIService myAPIService;

    private RetrofitUpdate() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.UPDATE_INFO)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(APIService.class);

    }

    public static synchronized RetrofitUpdate getInstance() {
        if (instance == null) {
            instance = new RetrofitUpdate();
        }
        return instance;
    }

    public APIService getMyApi() {
        return myAPIService;
    }

}
