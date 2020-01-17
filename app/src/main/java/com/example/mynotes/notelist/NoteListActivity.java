package com.example.mynotes.notelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.noteedit.NoteEditActivity;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.model.util.StyleSetup;
import com.example.mynotes.notedetails.NoteDetailsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity
        implements NoteListContract.View, View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private NoteListContract.Presenter presenter;

    private RecyclerView recyclerView;
    private NoteListAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list_view);
        new StyleSetup(this, getSupportActionBar());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton addNoteButton = findViewById(R.id.add_note_floating_button);
        ImageButton menuButton = findViewById(R.id.menu_button);
        addNoteButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);

        presenter = new NoteListPresenter(this.getApplicationContext(), this);
    }

    @Override
    public void goToDisplayNoteActivity(int id) {
        Intent intent = new Intent(this, NoteDetailsActivity.class);
        intent.putExtra(BundleExtraUtil.KEY_NOTE_ID, id);
        startActivity(intent);
    }

    @Override
    public void addNotesToListView(final List<Note> noteList) {
        //setup adapter
        viewAdapter = new NoteListAdapter(this, noteList, presenter);
        recyclerView.setAdapter(viewAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_note_floating_button:
                presenter.addNote("", "");
                Intent intent = new Intent(this, NoteEditActivity.class);
                intent.putExtra(BundleExtraUtil.KEY_NOTE_ID, presenter.getNewNoteIndex());
                startActivity(intent);
                break;

            case R.id.menu_button:
                showPopupMenu(v);
                break;
        }
    }

    private void showPopupMenu(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_popup_menu, popup.getMenu());
        new StyleSetup(this, getSupportActionBar());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //should change the visibility of the checkboxes and delete
        switch (item.getItemId()){
            case R.id.delete_notes_button:
                viewAdapter.deleteNotes();
                presenter.start(); // for updating the list
                return true;

            case R.id.select_all_notes_button:
                viewAdapter.setAllNotesChecked();
                presenter.start();
                return true;
        }
        return false;
    }

    @Override
    public void setPresenter(NoteListContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
