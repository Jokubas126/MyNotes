package com.example.mynotes.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.mynotes.R;
import com.example.mynotes.adapters.RecyclerViewAdapter;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.presenters.NoteListActivityPresenter;
import com.example.mynotes.view.StyleSetup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity
        implements NoteListActivityPresenter.NoteListActivityView, View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private NoteListActivityPresenter presenter;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter viewAdapter;

    private FloatingActionButton addNoteButton;
    private ImageButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list_view);
        new StyleSetup(this, getSupportActionBar());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addNoteButton = findViewById(R.id.add_note_floating_button);
        menuButton = findViewById(R.id.menu_button);
        addNoteButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);

        presenter = new NoteListActivityPresenter(this, this);

        /*presenter.addNote("first title","first text");
        presenter.addNote("second title","second text");
        presenter.addNote("third title","third text");
        presenter.addNote("fourth title","fourth text");
        presenter.addNote("fifth title","fifth text");
        presenter.addNote("sixth title","sixth text");
        presenter.addNote("seventh title","seventh text");
        presenter.addNote("eight title","eight text");
        presenter.addNote("ninth title","ninth text");*/

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
        switch(v.getId()){
            case R.id.add_note_floating_button:
                startActivity(new Intent(this, EditNoteActivity.class));
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
                presenter.loadAllNotes(); // for updating the list
                return true;

            /*case R.id.select_notes_button:
                viewAdapter.changeCheckboxVisibility(R.id.select_notes_button);
                presenter.loadAllNotes();
                return true;*/

            case R.id.select_all_notes_button:
                viewAdapter.setAllNotesChecked();
                presenter.loadAllNotes();
                return true;
        }
        return false;
    }
}
