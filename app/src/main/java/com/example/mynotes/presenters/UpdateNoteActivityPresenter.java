package com.example.mynotes.presenters;

import com.example.mynotes.model.handlers.DBHandler;

public class UpdateNoteActivityPresenter {

    UpdateNoteActivityView view;
    DBHandler handler;

    public interface UpdateNoteActivityView{
        void updateText();
    }

    public void updateNote(int index){
        handler.updateNote(handler.getNote(index));

    }
}
