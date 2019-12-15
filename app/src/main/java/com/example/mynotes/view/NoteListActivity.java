package com.example.mynotes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mynotes.R;
import com.example.mynotes.adapters.RecyclerViewAdapter;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.presenters.NoteListActivityPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity
        implements NoteListActivityPresenter.NoteListActivityView, View.OnClickListener {

    private NoteListActivityPresenter presenter;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter viewAdapter;

    private FloatingActionButton addNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list_view);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addNoteButton = findViewById(R.id.add_note_floating_button);
        addNoteButton.setOnClickListener(this);

        presenter = new NoteListActivityPresenter(this, this);
        presenter.loadAllNotes();
    }

    @Override
    public void addNoteToListView(final List<Note> noteList) {
        //setup adapter
        viewAdapter = new RecyclerViewAdapter(this.getApplicationContext(), noteList, presenter);
        recyclerView.setAdapter(viewAdapter);
    }

    @Override
    public void goToDisplayNoteActivity(int id) {
        Intent intent = new Intent(this, DisplayNoteActivity.class);
        intent.putExtra(BundleExtraUtil.KEY_NOTE_ID, id);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, EditNoteActivity.class));
    }
}
