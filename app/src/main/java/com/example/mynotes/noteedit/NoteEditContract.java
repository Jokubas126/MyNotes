package com.example.mynotes.noteedit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.example.mynotes.BasePresenter;
import com.example.mynotes.BaseView;

interface NoteEditContract {

    interface View extends BaseView<Presenter>{
        void displayInformation(String title, String content, Bitmap image);

        void goToDetailsActivity();
    }

    interface Presenter extends BasePresenter{

        void saveNoteState(String title, String content);

        Intent getIntentForImage();

        int getRequestCodeForImage();

        void addImage(Intent data);

        void removeImage();

        void loadRecorder(Context context);

        void dismissPopupWindow();

        int getId();
    }
}
