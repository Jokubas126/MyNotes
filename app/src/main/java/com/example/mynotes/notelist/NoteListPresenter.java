package com.example.mynotes.notelist;

import android.content.Context;
import android.util.Log;

import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class NoteListPresenter implements NoteListContract.Presenter {
    private NoteListContract.View view;
    private DBHandler handler;

    NoteListPresenter(Context context, NoteListContract.View view) {
        this.view = view;
        this.handler = new DBHandler(context); //do all the db handler stuff in separate thread
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
        List<Note> list = new ArrayList<>();

        List<Note> noteList = handler.getNoteList();
        for(Note note : noteList){
            Log.d("NoteListPresenter", "onLoadNotes " + note.getId() + " "  + note.getTitle() + " " + note.getContent());
            list.add(note);
        }
        //add to our listview
        view.addNotesToListView(list);
    }

    @Override
    public void onNoteClicked(int id){
        if (handler.getNote(id) != null){
            view.goToDisplayNoteActivity(id);
        }
    }

    @Override
    public int getNewNoteIndex(){
        return handler.getCount();
    }
}
