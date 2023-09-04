package com.assignment.rickandmorty.view;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;

import com.assignment.rickandmorty.data.roomdatabase.CharacterDatabaseClient;
import com.assignment.rickandmorty.databinding.ActivityMainBinding;
import com.assignment.rickandmorty.repository.model.Result;
import com.assignment.rickandmorty.viewmodel.MainActivityViewModel;

import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

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
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getItems().observe(this, items -> {
            if (items == null) return;
            saveCharacterList(items.getResults());
            binding.mainLL.setVisibility(View.VISIBLE);
            binding.loadingLL.setVisibility(View.GONE);
            NEXT_PAGE_URL = items.getInfo().getNext();
            characters.addAll(items.getResults());
            characterGridAdapter.notifyDataSetChanged();
        });
        mainActivityViewModel.getLoading().observe(this, isLoading -> {

            this.isLoading = isLoading;


        });
        setScrollViewObserver();
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
            public void hasSaccess( List<Result> resultList) {

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