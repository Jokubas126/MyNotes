package com.example.mynotes.noteedit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mynotes.R;
import com.example.mynotes.model.util.ActivityUtils;
import com.example.mynotes.model.util.StyleSetup;


public class NoteEditActivity extends AppCompatActivity {

    private NoteEditFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        new StyleSetup(this, getSupportActionBar());

        fragment = new NoteEditFragment();

        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                fragment, R.id.edit_note_container);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
