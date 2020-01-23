package com.example.mynotes.notedetails;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;
import com.example.mynotes.util.BundleExtraUtil;
import com.example.mynotes.util.ConversionUtil;
import com.example.mynotes.sharedviews.AudioCard;

public class NoteDetailsPresenter implements NoteDetailsContract.Presenter{

    private NoteDetailsContract.View view;
    private Activity activity;
    private View fragmentView;
    private Bundle bundle;
    private DBHandler dbHandler;
    private Note currentNote;

    NoteDetailsPresenter(Activity activity, NoteDetailsContract.View view, View fragmentView, Bundle bundle) {
        this.view = view;
        view.setPresenter(this);
        this.activity = activity;
        this.fragmentView = fragmentView;
        this.dbHandler = new DBHandler(activity);
        this.bundle = bundle;
        start();
    }

    public void start() {
        getInformation();
    }

    private void getInformation(){
        if (bundle != null){
            int id = bundle.getInt(BundleExtraUtil.KEY_NOTE_ID);
            currentNote = dbHandler.getNote(id);
            loadInformation();
        }
    }

    private void loadInformation(){
        if (currentNote.getImageUriString() != null){
            view.setViewInformation(currentNote.getTitle(), currentNote.getContent(),
                    ConversionUtil.stringUriToBitmap(
                            activity.getApplicationContext(),
                            currentNote.getImageUriString()));
        } else {
            view.setViewInformation(currentNote.getTitle(), currentNote.getContent(), null);
        }

        if(currentNote.getAudioFilePath() != null)
            loadPlayer();
    }

    private void loadPlayer(){
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View audioCardView = inflater.inflate(R.layout.card_audio, null);
        ViewGroup audioViewGroup = fragmentView.findViewById(R.id.audio_card_container);
        audioViewGroup.addView(audioCardView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        new AudioCard(fragmentView, currentNote.getAudioFilePath(), currentNote.getAudioFileTitle());
    }

    public int getNoteId() {
        return currentNote.getId();
    }
}
