package com.example.mynotes.notelist;

import com.example.mynotes.BasePresenter;
import com.example.mynotes.BaseView;
import com.example.mynotes.model.data.Note;

import java.util.List;

interface NoteListContract {

    interface View extends BaseView<Presenter> {
        void addNotesToListView(List<Note> titleList);

        void goToDisplayNoteActivity(int id);
    }

    interface Presenter extends BasePresenter {
        void deleteNote(int index);

        void addNote(String title, String content);

        void onNoteClicked(int id);

        int getNewNoteIndex();
    }
}
