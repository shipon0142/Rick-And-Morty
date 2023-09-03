package com.assignment.rickandmorty.repository.api;

import com.assignment.rickandmorty.repository.model.Character;
import com.assignment.rickandmorty.repository.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("character")
    Call<Character> fetchCharacters();

    @GET("character/{id}")
    Call<Result> fetchCharacterDetails(@Path("id")int id);
}
