package com.example.mynotes.util;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mynotes.R;

public class StyleSetup {

    public StyleSetup(AppCompatActivity activity, ActionBar actionBar){
        setup(activity, actionBar);
    }

    private void setup(AppCompatActivity activity, ActionBar actionBar){
        try {
            actionBar.hide();
            activity.setTheme(R.style.AppTheme);
        } catch (Exception e){ System.out.println("No action bar found to hide"); }
    }
}
