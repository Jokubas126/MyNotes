package com.example.mynotes.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mynotes.R;
import com.example.mynotes.presenters.EditNoteActivityPresenter;

public class EditNoteActivity extends AppCompatActivity
        implements EditNoteActivityPresenter.EditNoteActivityView {

    private EditNoteActivityPresenter presenter;

    private ImageButton confirmButton;
    private EditText titleEditText;
    private EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        titleEditText = findViewById(R.id.title_edit_text);
        contentEditText = findViewById(R.id.content_edit_text);
        confirmButton = findViewById(R.id.confirm_button);

        presenter = new EditNoteActivityPresenter(this, this);
        presenter.getInformation(getIntent().getExtras());
    }

    @Override
    public void displayInformation(String title, String content) {
        titleEditText.setText(title);
        contentEditText.setText(content);
    }

    @Override
    public void updateText() {

    }
}
