package com.assignment.rickandmorty.repository.api;

import com.assignment.rickandmorty.repository.model.Character;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("character")
    Call<Character> fetchData();
}
