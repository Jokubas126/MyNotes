package com.example.mynotes.notedetails;

import android.graphics.Bitmap;

import com.example.mynotes.BasePresenter;
import com.example.mynotes.BaseView;

interface NoteDetailsContract {

    interface View extends BaseView<Presenter> {
        void goToEditNoteActivity();
        void setViewInformation(String title, String content, Bitmap image);
    }

    interface Presenter extends BasePresenter{
        int getNoteId();
        void loadInformation();
    }
}
