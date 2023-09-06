package com.assignment.rickandmorty.view.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.assignment.rickandmorty.R;
import com.assignment.rickandmorty.databinding.ActivityCharacterDetailsBinding;
import com.assignment.rickandmorty.repository.model.Result;
import com.assignment.rickandmorty.utils.StatusColorCode;
import com.assignment.rickandmorty.utils.Util;
import com.assignment.rickandmorty.viewmodel.CharacterActivityViewModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class CharacterDetailsActivity extends AppCompatActivity {
    CharacterActivityViewModel characterActivityViewModel;
    ActivityCharacterDetailsBinding binding;
    private int character_id;
    private Result character;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCharacterDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        character_id = getIntent().getIntExtra("character_id", 0);
        String characterJson = getIntent().getStringExtra("character");
        character=new Gson().fromJson(characterJson,Result.class);
        binding.loadingLL.setVisibility(View.VISIBLE);
        binding.mainLLLL.setVisibility(View.GONE);
        init();
        setAll(character);
    }

    private void init() {

        characterActivityViewModel = new ViewModelProvider(this).get(CharacterActivityViewModel.class);
        characterActivityViewModel.setCharacterId(character_id);
        characterActivityViewModel.getItem().observe(this, character -> {
            if (character != null) {

                setAll(character);
            }

        });
        binding.refreshSL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!Util.hasInternet(CharacterDetailsActivity.this)){
                    Toast.makeText(CharacterDetailsActivity.this, R.string.no_internet_connection,Toast.LENGTH_SHORT).show();
                    binding.refreshSL.setRefreshing(false);
                    return;
                }
                characterActivityViewModel.callRefresh(character_id);
            }
        });
        binding.backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setAll(Result character) {
        binding.loadingLL.setVisibility(View.GONE);
        binding.mainLLLL.setVisibility(View.VISIBLE);
        binding.refreshSL.setRefreshing(false);
        binding.nameTV.setText(character.getName());
        binding.locationTV.setText(character.getLocation().getName());
        binding.speciesTV.setText(character.getSpecies());
        binding.genderTV.setText(character.getGender());
        binding.originTV.setText(character.getOrigin().getName());
        binding.statusTV.setText(character.getStatus());
        Picasso.get().load(character.getImage()).into(binding.characterIV);
        Picasso.get().load(R.drawable.rick_morty_banner2).into(binding.backgroundIV);
        if (character.getStatus().equalsIgnoreCase("alive"))
            binding.statusTV.getBackground().setColorFilter(Color.parseColor(StatusColorCode.ALIVE_COLOR.getCode()), PorterDuff.Mode.SRC_ATOP);
        else if (character.getStatus().equalsIgnoreCase("dead"))
            binding.statusTV.getBackground().setColorFilter(Color.parseColor(StatusColorCode.DEAD_COLOR.getCode()), PorterDuff.Mode.SRC_ATOP);
        else
            binding.statusTV.getBackground().setColorFilter(Color.parseColor(StatusColorCode.UNKNOWN_COLOR.getCode()), PorterDuff.Mode.SRC_ATOP);
    }


}