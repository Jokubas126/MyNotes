package com.example.mynotes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.mynotes.R;
import com.example.mynotes.adapters.RecyclerViewAdapter;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.presenters.NoteListActivityPresenter;

import java.util.List;

public class NoteListActivity extends AppCompatActivity
        implements NoteListActivityPresenter.NoteListActivityView {

    NoteListActivityPresenter presenter;

    private RecyclerView recyclerView;
    RecyclerViewAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list_view);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter = new NoteListActivityPresenter(
                this, new DBHandler(NoteListActivity.this));
        presenter.loadAllNotes();
    }

    @Override
    public void setNoteTitle(String title){}

    @Override
    public void setNoteText(String text){}

    @Override
    public void addNoteToListView(final List<Note> noteList) {
        //setup adapter
        viewAdapter = new RecyclerViewAdapter(this.getApplicationContext(), noteList, presenter);
        recyclerView.setAdapter(viewAdapter);
    }

    @Override
    public void goToDisplayNoteActivity(String titleText, String contentText) {
        Intent intent = new Intent(this, DisplayNoteActivity.class);
        intent.putExtra(BundleExtraUtil.NOTE_TITLE_TEXT, titleText);
        intent.putExtra(BundleExtraUtil.NOTE_CONTENT_TEXT, contentText);
        startActivity(intent);
    }
}
