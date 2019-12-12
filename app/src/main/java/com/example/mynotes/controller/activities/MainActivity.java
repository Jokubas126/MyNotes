package com.example.mynotes.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.handlers.DBHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DBHandler(MainActivity.this);

        /*List<Note> noteList = handler.getNoteList();
        for(Note note : noteList){
            Log.d("Main Activity", "onCreate: " + note.getId() + " "
                            + note.getTitle() + " " + note.getText());
        }*/
        
    }
}
