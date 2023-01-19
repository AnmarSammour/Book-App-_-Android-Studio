package com.example.planetofbooks.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFavInsert { private static RetrofitFavInsert instance = null;
    private APIService myAPIService;

    private RetrofitFavInsert() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.FAV_INSERT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(APIService.class);

    }

    public static synchronized RetrofitFavInsert getInstance() {
        if (instance == null) {
            instance = new RetrofitFavInsert();
        }
        return instance;
    }

    public APIService getMyApi() {
        return myAPIService;
    }

}
