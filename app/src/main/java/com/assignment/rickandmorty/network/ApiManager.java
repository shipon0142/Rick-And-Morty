package com.assignment.rickandmorty.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.assignment.rickandmorty.repository.api.ApiInterface;
import com.assignment.rickandmorty.repository.model.Character;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {

    private final ApiInterface apiService;

    public ApiManager() {
        apiService = RetrofitAPIClient.getApiClient();
    }
    public MutableLiveData<Character> getAllCharecters() {
        MutableLiveData<Character> data = new MutableLiveData<>();

        apiService.fetchData().enqueue(new Callback<Character>() {
            @Override
            public void onResponse(@NonNull Call<Character> call, @NonNull Response<Character> response) {
                assert response.body() != null;
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Character> call, @NonNull Throwable t) {

            }
        });

        return data;
    }
}
