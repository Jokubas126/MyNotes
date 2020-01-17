package com.example.mynotes.notedetails;

import android.app.Activity;
import android.os.Bundle;

import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.model.util.ConversionUtil;

public class NoteDetailsPresenter implements NoteDetailsContract.Presenter {

    private NoteDetailsContract.View view;
    private Activity activity;
    private Bundle bundle;
    private DBHandler handler;
    private Note currentNote;

    NoteDetailsPresenter(Activity activity, NoteDetailsContract.View view, Bundle bundle) {
        this.view = view;
        view.setPresenter(this);
        this.activity = activity;
        this.handler = new DBHandler(activity);
        this.bundle = bundle;
        start();
    }

    public void start() {
        getInformation();
    }

    private void getInformation(){
        if (bundle != null){
            int id = bundle.getInt(BundleExtraUtil.KEY_NOTE_ID);
            currentNote = handler.getNote(id);
        }
    }

    public void loadInformation(){
        if (currentNote.getImageUriString() != null){
            view.setViewInformation(currentNote.getTitle(), currentNote.getContent(),
                    ConversionUtil.stringUriToBitmap(
                            activity.getApplicationContext(),
                            currentNote.getImageUriString()));
        } else {
            view.setViewInformation(currentNote.getTitle(), currentNote.getContent(), null);
        }

    }

    public int getNoteId() {
        return currentNote.getId();
    }


}
