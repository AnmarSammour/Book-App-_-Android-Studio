package com.example.planetofbooks.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDeleteFav {

    private static RetrofitDeleteFav instance = null;
    private APIService myAPIService;

    private RetrofitDeleteFav() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.DELETE_FAV)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(APIService.class);

    }

    public static synchronized RetrofitDeleteFav getInstance() {
        if (instance == null) {
            instance = new RetrofitDeleteFav();
        }
        return instance;
    }

    public APIService getMyApi() {
        return myAPIService;
    }

}
