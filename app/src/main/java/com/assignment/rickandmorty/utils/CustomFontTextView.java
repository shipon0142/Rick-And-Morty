package com.assignment.rickandmorty.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class CustomFontTextView extends TextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLetterSpacing(0.03f);
        applyCustomFont(context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }
    public static Typeface selectTypeface(Context context, int textStyle) {

        switch (textStyle) {
            case Typeface.BOLD: // bold
                return FontCache.getTypeface(context, "fonts/Urbanist-SemiBold.ttf");

            case Typeface.NORMAL: // regular
                return FontCache.getTypeface(context, "fonts/Urbanist-Regular.ttf");

            case Typeface.ITALIC: // italic
                return FontCache.getTypeface(context, "fonts/Urbanist-Medium.ttf");

            case Typeface.BOLD_ITALIC: // bold italic
                return FontCache.getTypeface(context, "fonts/Urbanist-Medium.ttf");

            default:
                return FontCache.getTypeface(context, "fonts/Urbanist-Medium.ttf");
        }
    }



}