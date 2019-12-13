package com.example.mynotes.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mynotes.R;
import com.example.mynotes.model.handlers.DBHandler;
import com.example.mynotes.presenter.NoteListActivityPresenter;

import java.util.List;

public class NoteListActivity extends AppCompatActivity
        implements NoteListActivityPresenter.NoteListActivityView {

    NoteListActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new NoteListActivityPresenter(this, new DBHandler(NoteListActivity.this));


    }

    @Override
    public void setNoteTitle(String title) {

    }

    @Override
    public void setNoteText(String text) {

    }
}
