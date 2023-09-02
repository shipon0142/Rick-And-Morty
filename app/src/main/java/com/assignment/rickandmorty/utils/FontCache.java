package com.assignment.rickandmorty.utils;

import android.content.Context;
import android.graphics.Typeface;
import java.util.HashMap;
import java.util.Map;


public class FontCache {

    private static Map<String, Typeface> cachedFonts = new HashMap<String, Typeface>();

    public static Typeface getTypeface(Context context, String assetPath) {
        if (!cachedFonts.containsKey(assetPath)) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), assetPath);
            cachedFonts.put(assetPath, tf);
        }

        return cachedFonts.get(assetPath);
    }

}
