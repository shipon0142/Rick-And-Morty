package com.assignment.rickandmorty.repository.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class LocationConverter {

    @TypeConverter
    public static Location fromString(String value) {
        return new Gson().fromJson(value, Location.class);
    }

    @TypeConverter
    public static String toString(Location location) {
        return new Gson().toJson(location);
    }

}
