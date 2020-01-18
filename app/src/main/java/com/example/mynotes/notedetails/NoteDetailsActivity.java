package com.example.mynotes.notedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.mynotes.R;
import com.example.mynotes.util.ActivityUtils;
import com.example.mynotes.notelist.NoteListActivity;
import com.example.mynotes.util.StyleSetup;

public class NoteDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);
        new StyleSetup(this, getSupportActionBar());

        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                new NoteDetailsFragment(), R.id.display_note_container);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NoteListActivity.class));
            }
        });
    }
}
