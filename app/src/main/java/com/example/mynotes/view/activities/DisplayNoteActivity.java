package com.example.mynotes.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.mynotes.R;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.presenters.DisplayNoteActivityPresenter;
import com.example.mynotes.view.StyleSetup;

public class DisplayNoteActivity extends AppCompatActivity
        implements View.OnClickListener {

    private DisplayNoteActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);
        new StyleSetup(this, getSupportActionBar());

        presenter = new DisplayNoteActivityPresenter(this);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        presenter.getInformation(this, getIntent().getExtras());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // need to put goToEditNoteActivity() on background click

            case R.id.back_button:
                startActivity(new Intent(this, NoteListActivity.class));
                break;
        }
    }

    private void goToEditNoteActivity(){
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(BundleExtraUtil.KEY_NOTE_ID, presenter.getNoteId());
        startActivity(intent);
    }
}
