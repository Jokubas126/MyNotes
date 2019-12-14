package com.example.mynotes.presenters;

import android.util.Log;

import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivityPresenter {
    private NoteListActivityView view;
    private DBHandler handler;

    public NoteListActivityPresenter(NoteListActivityView view, DBHandler dbHandler) {
        this.view = view;
        this.handler = dbHandler;
    }

    public interface NoteListActivityView {
        void setNoteTitle(String title);
        void setNoteText(String text);
        void addNoteTitleToListView(List<Note> titleList);
    }

    public void addNote(String title, String text){
        handler.addNote(new Note(title, text));
    }

    public void deleteNote(int index){
        handler.deleteNote(handler.getNote(index));
    }



    public void loadNote(int index){
        Note note = handler.getNote(index);
        Log.d("NoteListPresenter", "loadNote: " + note.getTitle() + " " + note.getText() + "IT WORKS!");
        //view.setNoteTitle(note.getTitle());
        //view.setNoteText(note.getText());
    }

    public void loadAllNotes(){
        List<Note> noteTitleList = new ArrayList<>();

        List<Note> noteList = handler.getNoteList();
        for(Note note : noteList){
            Log.d("NoteListPresenter", "onLoadNotes " + note.getId() + " "  + note.getTitle() + " " + note.getText());
            noteTitleList.add(note);
        }
        //add to our listview
        view.addNoteTitleToListView(noteTitleList);
    }
}
