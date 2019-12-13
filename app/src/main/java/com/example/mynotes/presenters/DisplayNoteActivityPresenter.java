package com.example.mynotes.presenters;

import android.os.Bundle;

import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.view.DisplayNoteActivity;

public class DisplayNoteActivityPresenter {
    private DisplayNoteActivityView view;


    public DisplayNoteActivityPresenter(DisplayNoteActivity view) {
        this.view = view;
    }

    public interface DisplayNoteActivityView{
        void displayInformation(String title, String content);
    }

    public void getInformation(Bundle extras){
        String titleString = "";
        String contentString = "";

        if (extras != null){
            titleString = extras.getString(BundleExtraUtil.NOTE_TITLE_TEXT);
            contentString = extras.getString(BundleExtraUtil.NOTE_CONTENT_TEXT);
        }
        view.displayInformation(titleString, contentString);
    }

}
