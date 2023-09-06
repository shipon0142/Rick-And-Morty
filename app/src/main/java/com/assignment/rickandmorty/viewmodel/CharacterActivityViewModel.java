package com.assignment.rickandmorty.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assignment.rickandmorty.network.ApiManager;
import com.assignment.rickandmorty.repository.model.Result;

import retrofit2.Response;

public class CharacterActivityViewModel extends ViewModel {


    private final ApiManager apiManager;
    private MutableLiveData<Result> results;
    private int character_id;


    public CharacterActivityViewModel() {
        apiManager = new ApiManager();
        results = new MutableLiveData<>();
    }


    public CharacterActivityViewModel(ApiManager apiManager) {
        this.apiManager = apiManager;
        results = new MutableLiveData<>();
    }

    public void setCharacterId(int characterId) {
        this.character_id = characterId;
        apiManager.getCharecterDetails(character_id, new ApiManager.CharacterCallBack() {
            @Override
            public void onCharacterCallback(Response<Result> character) {
                if (character != null) results.setValue(character.body());
            }
        });
    }

    public void callRefresh(int characterId) {
        this.character_id = characterId;
        apiManager.getCharecterDetails(character_id, new ApiManager.CharacterCallBack() {
            @Override
            public void onCharacterCallback(Response<Result> result) {
                if (result != null) results.setValue(result.body());
            }
        });
    }

    public MutableLiveData<Result> getItem() {
        return results;
    }
}
