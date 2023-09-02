package com.assignment.rickandmorty.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.rickandmorty.databinding.AdapterCharecterBinding;
import com.assignment.rickandmorty.repository.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CharacterGridAdapter extends BaseAdapter {
    Context context;
    ArrayList<Result> results;

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

        nameTV.setText(results.get(position).getName());
        Picasso.get().load(results.get(position).getImage()).into(image);
        image.setClipToOutline(true);

        return convertView;
    }

}