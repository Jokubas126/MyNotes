package com.example.mynotes.notelist;

import com.example.mynotes.BasePresenter;
import com.example.mynotes.BaseView;

interface NoteListContract {

    interface View extends BaseView<Presenter> {

        void goToNoteEditActivity();
        void goToNoteDetailsActivity(int id);
    }

    interface Presenter extends BasePresenter {
        void deleteNote(int index);

        void addNote(String title, String content);

        void onNoteClicked(int id);

        int getNewNoteIndex();

        void showPopupMenu(android.view.View v);
    }
}
