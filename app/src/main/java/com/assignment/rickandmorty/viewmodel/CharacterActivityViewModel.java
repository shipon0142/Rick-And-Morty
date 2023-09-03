package com.assignment.rickandmorty.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.assignment.rickandmorty.network.ApiManager;
import com.assignment.rickandmorty.repository.model.Result;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
@HiltViewModel
public class CharacterActivityViewModel extends ViewModel {


    private final ApiManager apiManager;
    private MutableLiveData<Result> results;
    private int character_id;

    @Inject
    public CharacterActivityViewModel() {
        apiManager = new ApiManager();
        results = null;
    }
    public void setCharacterId(int characterId) {
        this.character_id = characterId;
        this.results=apiManager.getCharecterDetails(character_id);
    }
    public MutableLiveData<Result> getItems() {
        return results;
    }

}
