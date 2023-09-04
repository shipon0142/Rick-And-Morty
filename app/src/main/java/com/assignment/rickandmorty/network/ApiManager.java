package com.assignment.rickandmorty.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.assignment.rickandmorty.repository.api.ApiInterface;
import com.assignment.rickandmorty.repository.model.Character;
import com.assignment.rickandmorty.repository.model.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {

    private final ApiInterface apiService;

    public ApiManager() {
        apiService = RetrofitAPIClient.getApiClient();
    }
    public interface CharacterListCallBack{
        public void getharacterList(Response<Character> response);
    }

    public void getAllCharecters(int page,CharacterListCallBack characterListCallBack) {

        apiService.fetchCharacters(page).enqueue(new Callback<Character>() {
            @Override
            public void onResponse(@NonNull Call<Character> call, @NonNull Response<Character> response) {
                assert response.body() != null;
                characterListCallBack.getharacterList(response);
            }

            @Override
            public void onFailure(@NonNull Call<Character> call, @NonNull Throwable t) {
                characterListCallBack.getharacterList(null);
            }
        });


    }
    public MutableLiveData<Result> getCharecterDetails(int id) {
        MutableLiveData<Result> data = new MutableLiveData<>();

        apiService.fetchCharacterDetails(id).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if(response.code()==200) {
                    assert response.body() != null;
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {

            }
        });

        return data;
    }
}
