package com.assignment.rickandmorty;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.assignment.rickandmorty.network.ApiManager;
import com.assignment.rickandmorty.repository.model.Character;
import com.assignment.rickandmorty.viewmodel.MainActivityViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import retrofit2.Response;


@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ApiManager apiManager;

    @Mock
    private Observer<Boolean> loadingObserver;

    @Mock
    private Observer<Character> charactersObserver;
    @Mock
    private MainActivityViewModel viewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new MainActivityViewModel(apiManager);
        viewModel.getLoadingLiveData().observeForever(loadingObserver);
        viewModel.getItems().observeForever(charactersObserver);
    }

    @Test
    public void testSetPageNo() {
        int page = 2;
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ApiManager.CharacterListCallBack callback = invocation.getArgument(1);
                callback.getharacterList(Response.success(new Character()));
                return null;
            }
        }).when(apiManager).getAllCharecters(eq(page), any(ApiManager.CharacterListCallBack.class));

        viewModel.setPageNo(page);
        verify(loadingObserver, times(2)).onChanged(true);
        verify(loadingObserver).onChanged(false);
        verify(charactersObserver).onChanged(any(Character.class));
        verify(apiManager).getAllCharecters(eq(2), any(ApiManager.CharacterListCallBack.class));
    }

}
