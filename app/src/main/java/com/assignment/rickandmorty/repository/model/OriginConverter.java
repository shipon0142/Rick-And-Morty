package com.assignment.rickandmorty.repository.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class OriginConverter {

    @TypeConverter
    public static Origin fromString(String value) {
        return new Gson().fromJson(value, Origin.class);
    }

    @TypeConverter
    public static String toString(Origin origin) {
        return new Gson().toJson(origin);
    }

}
