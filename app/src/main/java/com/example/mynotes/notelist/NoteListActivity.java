package com.example.mynotes.notelist;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mynotes.R;
import com.example.mynotes.util.ActivityUtils;
import com.example.mynotes.util.StyleSetup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteListActivity extends AppCompatActivity {

    private NoteListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        new StyleSetup(this, getSupportActionBar());

        fragment = new NoteListFragment();

        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                fragment, R.id.note_list_container);

        FloatingActionButton addNoteButton = findViewById(R.id.add_note_floating_button);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.goToNoteEditActivity();
            }
        });

    }
}
