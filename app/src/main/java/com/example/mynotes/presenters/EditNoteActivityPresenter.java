package com.example.mynotes.presenters;

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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;
import com.example.mynotes.model.handlers.FileHandler;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.model.util.FileHandlerUtil;
import com.example.mynotes.view.fragments.EditTextFragment;
import com.example.mynotes.view.fragments.ImageFragment;
import com.example.mynotes.view.popup_window.RecorderWindow;

import java.io.File;

public class EditNoteActivityPresenter {

    private EditNoteActivityView view;
    private DBHandler dbHandler;
    private FileHandler fileHandler;

    private Note currentNote;
    private int noteIndex;

    private PopupWindow popupWindow;

    private EditTextFragment textFragment;

    public EditNoteActivityPresenter(Context context, EditNoteActivityView view) {
        this.view = view;
        dbHandler = new DBHandler(context);
        fileHandler = new FileHandler();
    }

    public interface EditNoteActivityView{
        void displayInformation(String title);
        void goToDisplayActivity(int id);
        void goToNoteListActivity();
        void showRecording();
    }

    public void getInformation(FragmentActivity activity, Bundle extras){
        if (extras != null){
            noteIndex = extras.getInt(BundleExtraUtil.KEY_NOTE_ID);
            currentNote = dbHandler.getNote(noteIndex);
            loadNote(activity);
        } else {
            currentNote = new Note("", "");
            loadNote(activity);
        }
    }

    public void loadNote(FragmentActivity activity){
        String titleString = currentNote.getTitle();
        view.displayInformation(titleString);

        FragmentManager manager = activity.getSupportFragmentManager();
        Bundle stringBundle = new Bundle();
        stringBundle.putString("note_text", currentNote.getContent());
        textFragment = new EditTextFragment();
        textFragment.setArguments(stringBundle);
        manager.beginTransaction().replace(R.id.edit_note_text_container, textFragment).commit();

        if (currentNote.getImageUriString() != null){
            String imagePath = currentNote.getImageUriString();

            Bundle imageBundle = new Bundle();
            imageBundle.putString("note_image", imagePath);

            ImageFragment imageFragment = new ImageFragment();
            imageFragment.setArguments(imageBundle);
            manager.beginTransaction().replace(R.id.edit_note_image_container, imageFragment).commit();
        }

        if(currentNote.getAudioFilePath() != null){
            view.showRecording();
        }
    }

    public void confirmNote(String newTitle){
        if (noteIndex != 0){
            Note note = dbHandler.getNote(noteIndex);
            note.setTitle(newTitle);
            note.setContent(textFragment.getString());
            note.setImageUriString(currentNote.getImageUriString());
            dbHandler.updateNote(note);
            view.goToDisplayActivity(note.getId());
        } else {
            Note note = new Note();
            note.setTitle(newTitle);
            note.setContent(textFragment.getString());
            note.setImageUriString(currentNote.getImageUriString());
            dbHandler.addNote(note);
            view.goToNoteListActivity();
        }
    }

    public void saveNoteState(String title){
        currentNote.setTitle(title);
        currentNote.setContent(textFragment.getString());
        dbHandler.updateNote(currentNote);
    }

    public Intent getIntentForImage(){
        return fileHandler.getIntentForImage();
    }

    public int getRequestCodeForImage(){
        return FileHandlerUtil.KEY_PICK_IMAGE;
    }

    public void loadImage(Intent data){
        if(data != null)
        {
            try{
                Uri imageUri = data.getData();

                if (imageUri != null) {
                    currentNote.setImageUriString(imageUri.toString());
                } else {
                    Log.d("EditNotePresenter", "loadImage: " + "loading image failed");
                }
                dbHandler.updateNote(currentNote);
                Log.d("EditNotePresenter", "loadImage: " + "image loaded");
            } catch(Exception e){
                Log.d("EditNotePresenter", "loadImage: " + "loading image failed");
            }
        }
    }

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

    public void saveRecording(File file){
        currentNote.setAudioFilePath(file.getPath());
        Log.d("SAVED AUDIO", "saveRecording: PATH IS" + file.getPath());
        dbHandler.updateNote(currentNote);
        view.showRecording();

        dismissPopupWindow();
    }

    public void dismissPopupWindow(){
        if(popupWindow != null)
            popupWindow.dismiss();
    }
}
