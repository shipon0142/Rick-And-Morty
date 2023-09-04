package com.assignment.rickandmorty.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assignment.rickandmorty.network.ApiManager;
import com.assignment.rickandmorty.repository.model.Character;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Response;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {


    private  ApiManager apiManager;
    private MutableLiveData<Character> charecters;
    private MutableLiveData<Boolean> loadingData = new MutableLiveData<>();


    int page=1;

    @Inject
    public MainActivityViewModel() {
        apiManager = new ApiManager();
        charecters=new MutableLiveData<>();
        loadingData.setValue(true);
        apiManager.getAllCharecters(page, new ApiManager.CharacterListCallBack() {
            @Override
            public void getharacterList(Response<Character> response) {
                loadingData.setValue(false);
                charecters.setValue(response.body());

            }
        });
    }

    public MutableLiveData<Boolean> getLoadingLiveData() {
        return loadingData;
    }
    public void setPageNo(int page) {
        this.page = page;
        loadingData.setValue(true);
       apiManager.getAllCharecters(page, new ApiManager.CharacterListCallBack() {
           @Override
           public void getharacterList(Response<Character> response) {
               loadingData.setValue(false);
               charecters.setValue(response.body());
           }


       });
    }
    public MutableLiveData<Character> getItems() {
        return charecters;
    } public MutableLiveData<Boolean> getLoading() {
        return loadingData;
    }

}
