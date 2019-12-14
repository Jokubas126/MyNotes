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

    public void updateNote(String newTitle, String newContent){

        Note note = handler.getNote(noteIndex);
        note.setTitle(newTitle);
        note.setText(newContent);
        handler.updateNote(note);
        view.goToDisplayActivity(note.getId());
    }
}
