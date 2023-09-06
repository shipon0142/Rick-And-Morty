package com.assignment.rickandmorty.view.activity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.assignment.rickandmorty.R;
import com.assignment.rickandmorty.data.roomdatabase.CharacterDatabaseClient;
import com.assignment.rickandmorty.databinding.ActivityMainBinding;
import com.assignment.rickandmorty.repository.model.Result;
import com.assignment.rickandmorty.utils.Util;
import com.assignment.rickandmorty.view.adapter.CharacterGridAdapter;
import com.assignment.rickandmorty.viewmodel.MainActivityViewModel;

import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

public class MainActivity extends AppCompatActivity {
    MainActivityViewModel mainActivityViewModel;
    ActivityMainBinding binding;
    private String NEXT_PAGE_URL;
    private ArrayList<Result> characters;
    private boolean isLoading;
    private CharacterGridAdapter characterGridAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        retriveCharacterData();
        setListener();
        setScrollViewObserver();
    }

    private void setListener() {
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getItems().observe(this, items -> {
            if (items == null) return;
            if (items.getInfo().getPrev() == null) {
                characters.clear();
                characters.addAll(items.getResults());
            } else {
                characters.addAll(items.getResults());
            }
            NEXT_PAGE_URL = items.getInfo().getNext();
            saveCharacterList(items.getResults());
            setAllValue(items.getResults());
        });
        mainActivityViewModel.getLoading().observe(this, isLoading -> {

            this.isLoading = isLoading;


        });
        binding.refreshLL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!Util.hasInternet(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    binding.refreshLL.setRefreshing(false);
                    return;
                }
                mainActivityViewModel.refreshPage(1);
            }
        });
    }

    private void setAllValue(ArrayList<Result> results) {
        binding.mainLL.setVisibility(View.VISIBLE);
        binding.loadingLL.setVisibility(View.GONE);
        binding.refreshLL.setRefreshing(false);
        characterGridAdapter.notifyDataSetChanged();
    }

    private void retriveCharacterData() {
        CharacterDatabaseClient.getInstance(this).retrieveCharacterList(new CharacterDatabaseClient.RetriveCharactersCallback() {
            @Override
            public void onCharactersRetrieved(ArrayList<Result> characterList) {
                Log.d("characters_size", "" + characterList.size());
                characters.addAll(characterList);
                setAllValue(characters);
            }
        });
    }

    private void setScrollViewObserver() {
        binding.scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) binding.scrollView.getChildAt(binding.scrollView.getChildCount() - 1);
                int diff = (view.getBottom() - (binding.scrollView.getHeight() + binding.scrollView.getScrollY()));
                Log.d("SCROLL_POSITION", "" + diff);
                if (diff == 0) {
                    if (NEXT_PAGE_URL != null && !NEXT_PAGE_URL.equals("") && characters != null && characters.size() > 0 && !isLoading) {

                        callNextPage();

                    }
                }
            }
        });
    }

    private void init() {
        characters = new ArrayList<>();
        setCharacterAdapter(characters);
        binding.loadingLL.setVisibility(View.VISIBLE);
        binding.mainLL.setVisibility(View.GONE);
    }

    private void saveCharacterList(ArrayList<Result> results) {
        CharacterDatabaseClient.getInstance(this).saveCharacterList(results, new CharacterDatabaseClient.SavedCharacterSuccessCallback() {
            @Override
            public void hasSaccess(ArrayList<Result> resultList) {

            }
        });
    }

    public static int extractPageNumber(String urlString) {
        try {
            Uri uri = Uri.parse(urlString);
            String pageQueryParam = uri.getQueryParameter("page");
            if (pageQueryParam != null) {
                return Integer.parseInt(pageQueryParam);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void callNextPage() {
        if (extractPageNumber(NEXT_PAGE_URL) != -1) {
            binding.loadMorePB.setVisibility(View.VISIBLE);
            mainActivityViewModel.setPageNo(extractPageNumber(NEXT_PAGE_URL));
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setCharacterAdapter(ArrayList<Result> results) {
        characterGridAdapter = new CharacterGridAdapter(MainActivity.this, results);
        binding.characterGV.setExpanded(true);
        binding.characterGV.setVerticalScrollBarEnabled(false);
        binding.characterGV.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }

        });
        binding.characterGV.setAdapter(characterGridAdapter);
    }
}