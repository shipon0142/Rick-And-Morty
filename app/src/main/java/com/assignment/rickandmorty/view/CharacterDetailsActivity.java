package com.assignment.rickandmorty.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.assignment.rickandmorty.R;
import com.assignment.rickandmorty.databinding.ActivityCharacterDetailsBinding;
import com.assignment.rickandmorty.repository.model.Result;
import com.assignment.rickandmorty.viewmodel.CharacterActivityViewModel;
import com.squareup.picasso.Picasso;

public class CharacterDetailsActivity extends AppCompatActivity {
    CharacterActivityViewModel characterActivityViewModel;
    ActivityCharacterDetailsBinding binding;
    private int character_id;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCharacterDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        character_id=getIntent().getIntExtra("character_id",0);
        characterActivityViewModel = new ViewModelProvider(this).get(CharacterActivityViewModel.class);
        characterActivityViewModel.setCharacterId(character_id);
        characterActivityViewModel.getItems().observe(this, character -> {
            if(character!=null) {
                setAll(character);
            }

        });
    }

    private void setAll(Result character) {

         binding.nameTV.setText(character.getName());
         binding.locationTV.setText(character.getLocation().getName());
         binding.speciesTV.setText(character.getSpecies());
         binding.genderTV.setText(character.getGender());
         binding.originTV.setText(character.getOrigin().getName());
         binding.statusTV.setText(character.getStatus());
         Picasso.get().load(character.getImage()).into(binding.characterIV);
         Picasso.get().load(R.drawable.rick_morty_banner2).into(binding.backgroundIV);

    }


}