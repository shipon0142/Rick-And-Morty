package com.assignment.rickandmorty.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.assignment.rickandmorty.databinding.ActivityMainBinding;
import com.assignment.rickandmorty.repository.model.Result;
import com.assignment.rickandmorty.viewmodel.MainActivityViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MainActivityViewModel mainActivityViewModel;
    ActivityMainBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getItems().observe(this, items -> {
            setCharacterAdapter(items.getResults());
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setCharacterAdapter(ArrayList<Result> results) {
        CharacterGridAdapter characterGridAdapter = new CharacterGridAdapter(MainActivity.this, results);
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