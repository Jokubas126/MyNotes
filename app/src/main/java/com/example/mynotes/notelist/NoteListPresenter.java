package com.example.mynotes.notelist;

import android.app.Activity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class NoteListPresenter implements NoteListContract.Presenter, PopupMenu.OnMenuItemClickListener {

    private NoteListContract.View view;
    private Activity activity;
    private DBHandler handler;
    private RecyclerView recyclerView;
    private NoteListAdapter viewAdapter;

    NoteListPresenter(Activity activity, NoteListContract.View view, RecyclerView recyclerView) {
        this.view = view;
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.handler = new DBHandler(activity); //do all the db handler stuff in separate thread
        view.setPresenter(this);
        start();
    }

    public void start() {
        loadAllNotes();
    }

    @Override
    public void deleteNote(int index){
        handler.deleteNote(handler.getNote(index));
    }

    @Override
    public void addNote(String title, String content){
        handler.addNote(new Note(title, content));
    }

    private void loadAllNotes(){
        List<Note> noteList = handler.getNoteList();

        viewAdapter = new NoteListAdapter(activity, new ArrayList<>(noteList), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(viewAdapter);
    }

    @Override
    public void onNoteClicked(int id){
        if (handler.getNote(id) != null){
            view.goToNoteDetailsActivity(id);
        }
    }

    @Override
    public int getNewNoteIndex(){
        return 0;
    }

    @Override
    public void showPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_popup_menu, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //should change the visibility of the checkboxes and delete
        switch (item.getItemId()){
            case R.id.delete_notes_button:
                viewAdapter.deleteNotes();
                loadAllNotes();
                return true;

            case R.id.select_all_notes_button:
                viewAdapter.setAllNotesChecked();
                loadAllNotes();
                return true;
        }
        return false;
    }
}
