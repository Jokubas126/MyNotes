package com.example.mynotes.presenters;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.view.fragments.DisplayTextFragment;
import com.example.mynotes.view.fragments.ImageFragment;

public class DisplayNoteActivityPresenter {
    private DBHandler handler;
    private Note currentNote;

    public DisplayNoteActivityPresenter(Context context) {
        this.handler = new DBHandler(context);
    }

    public void getInformation(FragmentActivity activity, Bundle extras){
        if (extras != null){
            int id = extras.getInt(BundleExtraUtil.KEY_NOTE_ID);
            currentNote = handler.getNote(id);
            loadFragments(activity);
        }
    }

    private void loadFragments(FragmentActivity activity){
        FragmentManager manager = activity.getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putString("title_text", currentNote.getTitle());
        bundle.putString("content_text", currentNote.getContent());

        DisplayTextFragment fragment = new DisplayTextFragment();
        fragment.setArguments(bundle);
        manager.beginTransaction().replace(R.id.display_note_text_container, fragment).commit();

        String uri = currentNote.getImageUriString();

        if (currentNote.getImageUriString() != null){
            String imagePath = currentNote.getImageUriString();

            Bundle imageBundle = new Bundle();
            imageBundle.putString("note_image", imagePath);

            ImageFragment imageFragment = new ImageFragment();
            imageFragment.setArguments(imageBundle);
            manager.beginTransaction().replace(R.id.display_note_image_container, imageFragment).commit();
        }
    }

    public int getNoteId() {
        return currentNote.getId();
    }
}
