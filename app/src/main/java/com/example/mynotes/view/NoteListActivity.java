package com.example.mynotes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.adapters.RecyclerViewAdapter;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.presenters.NoteListActivityPresenter;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity
        implements NoteListActivityPresenter.NoteListActivityView {

    NoteListActivityPresenter presenter;

    private RecyclerView recyclerView;
    RecyclerViewAdapter viewAdapter;

    TextView textTextView;
    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list_view);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter = new NoteListActivityPresenter(this, new DBHandler(NoteListActivity.this));
        presenter.loadAllNotes();
    }

    @Override
    public void setNoteTitle(String title) {
        titleTextView.setText(title);
    }

    @Override
    public void setNoteText(String text) {
        textTextView.setText(text);
    }

    @Override
    public void addNoteTitleToListView(final List<Note> noteList) {

        //setup adapter
        viewAdapter = new RecyclerViewAdapter(this.getApplicationContext(), noteList);

        recyclerView.setAdapter(viewAdapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //do something when certain item is clicked
                presenter.loadNote(noteList.get(position).getId());

                Intent intent = new Intent(NoteListActivity.this, DisplayNoteActivity.class);
                intent.putExtra(BundleExtraUtil.NOTE_TITLE_TEXT, noteList.get(position).getTitle());
                intent.putExtra(BundleExtraUtil.NOTE_CONTENT_TEXT, noteList.get(position).getText());
                startActivity(intent);
            }
        });*/
    }
}
