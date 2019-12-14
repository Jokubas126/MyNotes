package com.example.mynotes.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mynotes.R;
import com.example.mynotes.presenters.UpdateNoteActivityPresenter;

public class UpdateNoteActivity extends AppCompatActivity
        implements UpdateNoteActivityPresenter.UpdateNoteActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
    }

    @Override
    public void updateText() {

    }
}
