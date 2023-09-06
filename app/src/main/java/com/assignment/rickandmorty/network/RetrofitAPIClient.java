package com.assignment.rickandmorty.network;

import com.assignment.rickandmorty.repository.api.ApiInterface;
import com.assignment.rickandmorty.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPIClient {

    public static ApiInterface getApiClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }
}
