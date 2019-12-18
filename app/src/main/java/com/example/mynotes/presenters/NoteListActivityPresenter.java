package com.example.mynotes.presenters;

import android.content.Context;
import android.util.Log;

import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivityPresenter {
    private NoteListActivityView view;
    private DBHandler handler;

    public NoteListActivityPresenter(Context context, NoteListActivityView view) {
        this.view = view;
        this.handler = new DBHandler(context);
    }

    public interface NoteListActivityView {
        void addNoteToListView(List<Note> titleList);
        void goToDisplayNoteActivity(int id);
    }

    public void deleteNote(int index){
        handler.deleteNote(handler.getNote(index));
    }

    public void addNote(String title, String content){
        handler.addNote(new Note(title, content));
    }

    public void loadAllNotes(){
        List<Note> noteTitleList = new ArrayList<>();

        List<Note> noteList = handler.getNoteList();
        for(Note note : noteList){
            Log.d("NoteListPresenter", "onLoadNotes " + note.getId() + " "  + note.getTitle() + " " + note.getContent());
            noteTitleList.add(note);
        }
        //add to our listview
        view.addNoteToListView(noteTitleList);
    }



    public void onNoteClicked(int id){
        if (handler.getNote(id) != null){
            view.goToDisplayNoteActivity(id);
        }
    }
}
