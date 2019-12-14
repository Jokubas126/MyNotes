package com.example.mynotes.presenters;

import android.content.Context;
import android.os.Bundle;

import com.example.mynotes.model.handlers.DBHandler;
import com.example.mynotes.model.util.BundleExtraUtil;

public class EditNoteActivityPresenter {

    private EditNoteActivityView view;
    private DBHandler handler;

    public EditNoteActivityPresenter(Context context, EditNoteActivityView view) {
        this.view = view;
        handler = new DBHandler(context);
    }

    public interface EditNoteActivityView{
        void displayInformation(String title, String content);
        void updateText();
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

    public void updateNote(int index){
        handler.updateNote(handler.getNote(index));
    }
}
