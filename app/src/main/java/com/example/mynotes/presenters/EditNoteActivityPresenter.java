package com.example.mynotes.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.mynotes.model.util.FileHandlerUtil;
import com.example.mynotes.view.popup_window.RecorderWindow;

import java.io.InputStream;

public class EditNoteActivityPresenter {

    private EditNoteActivityView view;
    private DBHandler dbHandler;
    private FileHandler fileHandler;

    private Note currentNote;
    private int noteIndex;

    private PopupWindow popupWindow;

    public EditNoteActivityPresenter(Context context, EditNoteActivityView view) {
        this.view = view;
        dbHandler = new DBHandler(context);
        fileHandler = new FileHandler();
    }



    public interface EditNoteActivityView{
        void displayInformation(String title, String content, Bitmap image);
        void goToDisplayActivity(int id);
        void goToNoteListActivity();
    }

    public void getInformation(Bundle extras){
        if (extras != null){
            noteIndex = extras.getInt(BundleExtraUtil.KEY_NOTE_ID);
            currentNote = dbHandler.getNote(noteIndex);
            loadNote();
        }
    }

    public void loadNote(){
        String titleString = currentNote.getTitle();
        String contentString = currentNote.getContent();
        if (currentNote.getImage() != null){
            Bitmap image = currentNote.getImage();
            view.displayInformation(titleString, contentString, image);
        } else{
            view.displayInformation(titleString, contentString, null);
        }
    }

    public void confirmNote(String newTitle, String newContent){
        if (noteIndex != 0){
            Note note = dbHandler.getNote(noteIndex);
            note.setTitle(newTitle);
            note.setContent(newContent);
            dbHandler.updateNote(note);
            view.goToDisplayActivity(note.getId());
        } else {
            Note note = new Note();
            note.setTitle(newTitle);
            note.setContent(newContent);
            dbHandler.addNote(note);
            view.goToNoteListActivity();
        }
    }

    public Intent getIntentForImage(){
        return fileHandler.getIntentForImage();
    }

    public int getRequestCodeForImage(){
        return FileHandlerUtil.KEY_PICK_IMAGE;
    }

    public void loadImage(Context context, Intent data){
        if(data != null)
        {
            try{
                Uri imageUri = data.getData();
                InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
                Bitmap image = BitmapFactory.decodeStream(imageStream);
                currentNote.setImage(image);
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

    public void dismissPopupWindow(){
        if(popupWindow != null)
            popupWindow.dismiss();
    }
}
