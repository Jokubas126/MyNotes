package com.example.mynotes.noteedit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;


import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;
import com.example.mynotes.model.handlers.FileHandler;
import com.example.mynotes.util.BundleExtraUtil;
import com.example.mynotes.util.ConversionUtil;
import com.example.mynotes.util.FileHandlerUtil;
import com.example.mynotes.sharedviews.AudioCard;

public class NoteEditPresenter implements NoteEditContract.Presenter, RecorderPopupWindow.Listener, AudioCard.OnRemoveAudioListener {

    private NoteEditContract.View view;
    private Activity activity;
    private Bundle bundle;

    private DBHandler dbHandler;
    private FileHandler fileHandler;

    private Note currentNote;
    private int noteIndex;
    private boolean isSaved;

    private View fragmentView;
    private ViewGroup audioViewGroup;

    private PopupWindow popupWindow;

    NoteEditPresenter(Activity activity, NoteEditContract.View view, View fragmentView, Bundle bundle) {
        this.view = view;
        this.fragmentView = fragmentView;
        this.bundle = bundle;
        this.activity = activity;
        dbHandler = new DBHandler(activity.getApplicationContext());
        fileHandler = new FileHandler();
        view.setPresenter(this);
        start();
    }

    public void start() {
        getInformation(bundle);
    }

    private void getInformation(Bundle extras){
        if (extras != null){
            isSaved = true;
            noteIndex = extras.getInt(BundleExtraUtil.KEY_NOTE_ID);
            currentNote = dbHandler.getNote(noteIndex);
            loadNote();
        } else {
            isSaved = false;
            currentNote = new Note("", "");
            loadNote();
        }
    }

    private void loadNote(){
        if (currentNote.getImageUriString() != null)
            view.displayInformation(currentNote.getTitle(), currentNote.getContent(), ConversionUtil.stringUriToBitmap(activity, currentNote.getImageUriString()));
        else view.displayInformation(currentNote.getTitle(), currentNote.getContent(), null);

        if(currentNote.getAudioFilePath() != null){
            loadPlayer();
        }
    }

    @Override
    public void saveNoteState(String title, String content){
        //no need to pass image or audio recording, because they are already saved in the object currentNote
        currentNote.setTitle(title);
        currentNote.setContent(content);
        if (isSaved){
            dbHandler.updateNote(currentNote);
            currentNote = dbHandler.getNote(noteIndex);
        } else {
            dbHandler.addNote(currentNote);
            currentNote = dbHandler.getNoteList().get(dbHandler.getNoteList().size() - 1);
        }
    }

    @Override
    public Intent getIntentForImage(){
        return fileHandler.getIntentForImage();
    }

    @Override
    public int getRequestCodeForImage(){
        return FileHandlerUtil.KEY_PICK_IMAGE;
    }

    @Override
    public void addImage(Intent data){
        if(data != null)
        {
            try{
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    currentNote.setImageUriString(imageUri.toString());
                    loadNote();
                    Log.d("EditNotePresenter", "addImage: " + "image loaded");
                } else {
                    Log.d("EditNotePresenter", "addImage: " + "loading image failed");
                    throw new RuntimeException();
                }
            } catch(Exception e){
                currentNote.setImageUriString("");
                loadNote();
                Log.d("EditNotePresenter", "addImage: " + "loading image failed");
            }
        }
    }

    @Override
    public void loadRecorder(Context context){
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.record_audio_card_view, null);

        // create the popup window
         popupWindow = new PopupWindow(
            popupView,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            true);

        // show the popup window
        popupWindow.showAtLocation(new RelativeLayout(context), Gravity.CENTER, 0, 0);

        new RecorderPopupWindow(context, popupView, this);
    }

    @Override
    public void dismissPopupWindow(){
        if(popupWindow != null)
            popupWindow.dismiss();
    }

    @Override
    public int getId() {
        return currentNote.getId();
    }

    private void loadPlayer(){
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View audioCardView = inflater.inflate(R.layout.card_audio, null);
        audioViewGroup = fragmentView.findViewById(R.id.audio_card_container);
        audioViewGroup.addView(audioCardView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        new AudioCard(fragmentView, currentNote.getAudioFilePath(), this);
    }

    @Override
    public void recorderCallBack(String audioFilePath) {
        if (audioFilePath != null){
            currentNote.setAudioFilePath(audioFilePath);
            Log.d("SAVED AUDIO", "saveRecording: PATH IS" + audioFilePath);
            dbHandler.updateNote(currentNote);
            loadPlayer();
        }
        dismissPopupWindow();
    }

    @Override
    public void onAudioRemove() {
        audioViewGroup.removeAllViews();
        currentNote.setAudioFilePath(null);
        dbHandler.updateNote(currentNote);
    }
}
