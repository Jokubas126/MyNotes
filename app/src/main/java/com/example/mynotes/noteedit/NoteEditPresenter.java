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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;


import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;
import com.example.mynotes.model.handlers.FileHandler;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.model.util.ConversionUtil;
import com.example.mynotes.model.util.FileHandlerUtil;
import com.example.mynotes.recorder.RecorderWindow;

import java.io.File;
import java.util.List;

public class NoteEditPresenter implements NoteEditContract.Presenter {

    private NoteEditContract.View view;
    private Activity activity;
    private Bundle bundle;

    private DBHandler dbHandler;
    private FileHandler fileHandler;

    private Note currentNote;
    private int noteIndex;
    private boolean isSaved;


    private PopupWindow popupWindow;

    NoteEditPresenter(Activity activity, NoteEditContract.View view, Bundle bundle) {
        this.view = view;
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

        if(currentNote.getAudioFilePath() != null){
            view.showRecording();
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

        new RecorderWindow(context, popupView, this);
    }

    @Override
    public void saveRecording(File file){
        currentNote.setAudioFilePath(file.getPath());
        Log.d("SAVED AUDIO", "saveRecording: PATH IS" + file.getPath());
        dbHandler.updateNote(currentNote);
        view.showRecording();

        dismissPopupWindow();
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
}
