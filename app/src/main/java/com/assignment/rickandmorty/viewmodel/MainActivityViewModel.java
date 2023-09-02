package com.assignment.rickandmorty.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assignment.rickandmorty.network.ApiManager;
import com.assignment.rickandmorty.repository.model.Character;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {


    private final ApiManager apiManager;
    private final MutableLiveData<Character> charecters;

    @Inject
    public MainActivityViewModel() {
        apiManager = new ApiManager();
        charecters = apiManager.getAllCharecters();
    }

    public MutableLiveData<Character> getItems() {
        return charecters;
    }

}
