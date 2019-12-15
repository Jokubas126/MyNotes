package com.example.mynotes.presenters;

import android.content.Context;
import android.os.Bundle;

import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;
import com.example.mynotes.model.util.BundleExtraUtil;

public class EditNoteActivityPresenter {

    private EditNoteActivityView view;
    private DBHandler handler;
    private int noteIndex;

    public EditNoteActivityPresenter(Context context, EditNoteActivityView view) {
        this.view = view;
        handler = new DBHandler(context);
    }

    public interface EditNoteActivityView{
        void displayInformation(String title, String content);
        void goToDisplayActivity(int id);
        void goToNoteListActivity();
    }

    public void getInformation(Bundle extras){
        String titleString = "";
        String contentString = "";

        if (extras != null){
            noteIndex = extras.getInt(BundleExtraUtil.KEY_NOTE_ID);
            Note note = handler.getNote(noteIndex);
            titleString = note.getTitle();
            contentString = note.getContent();
        }
        view.displayInformation(titleString, contentString);
    }

    public void confirmNote(String newTitle, String newContent){
        if (noteIndex != 0){
            Note note = handler.getNote(noteIndex);
            note.setTitle(newTitle);
            note.setContent(newContent);
            handler.updateNote(note);
            view.goToDisplayActivity(note.getId());
        } else {
            Note note = new Note();
            note.setTitle(newTitle);
            note.setContent(newContent);
            handler.addNote(note);
            view.goToNoteListActivity();
        }
    }
}
