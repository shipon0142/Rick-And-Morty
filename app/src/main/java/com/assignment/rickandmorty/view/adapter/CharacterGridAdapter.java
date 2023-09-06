package com.assignment.rickandmorty.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.rickandmorty.R;
import com.assignment.rickandmorty.databinding.AdapterCharecterBinding;
import com.assignment.rickandmorty.repository.model.Result;
import com.assignment.rickandmorty.utils.StatusColorCode;
import com.assignment.rickandmorty.view.activity.CharacterDetailsActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CharacterGridAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Result> results;


    public CharacterGridAdapter(Context context, ArrayList<Result> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AdapterCharecterBinding binding;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            binding = AdapterCharecterBinding.inflate(layoutInflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (AdapterCharecterBinding) convertView.getTag();
        }

        ImageView image = binding.imageIV;
        TextView nameTV = binding.nameTV;
        TextView statusTV = binding.statusTV;
        if (results.get(position).getStatus().equalsIgnoreCase("alive"))
            statusTV.getBackground().setColorFilter(Color.parseColor(StatusColorCode.ALIVE_COLOR.getCode()), PorterDuff.Mode.SRC_ATOP);
        else if (results.get(position).getStatus().equalsIgnoreCase("dead"))
            statusTV.getBackground().setColorFilter(Color.parseColor(StatusColorCode.DEAD_COLOR.getCode()), PorterDuff.Mode.SRC_ATOP);
        else
            statusTV.getBackground().setColorFilter(Color.parseColor(StatusColorCode.UNKNOWN_COLOR.getCode()), PorterDuff.Mode.SRC_ATOP);
        nameTV.setText(results.get(position).getName());
        statusTV.setText(results.get(position).getStatus());
        Picasso.get().load(results.get(position).getImage()).placeholder(R.drawable.character_placeholder).into(image);
        image.setClipToOutline(true);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CharacterDetailsActivity.class);
                intent.putExtra("character_id", results.get(position).getId());
                intent.putExtra("character", new Gson().toJson(results.get(position)));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}