package com.example.mynotes.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mynotes.R;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.presenters.EditNoteActivityPresenter;
import com.example.mynotes.view.StyleSetup;

public class EditNoteActivity extends AppCompatActivity
        implements EditNoteActivityPresenter.EditNoteActivityView, View.OnClickListener {

    private EditNoteActivityPresenter presenter;

    private ImageButton confirmButton;
    private EditText titleEditText;
    private EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        new StyleSetup(this, getSupportActionBar());

        titleEditText = findViewById(R.id.title_edit_text);
        contentEditText = findViewById(R.id.content_edit_text);
        confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(this);

        presenter = new EditNoteActivityPresenter(this, this);
        presenter.getInformation(getIntent().getExtras());
    }

    @Override
    public void displayInformation(String title, String content) {
        titleEditText.setText(title);
        contentEditText.setText(content);
    }

    @Override
    public void goToDisplayActivity(int id) {
        Intent intent = new Intent(this, DisplayNoteActivity.class);
        intent.putExtra(BundleExtraUtil.KEY_NOTE_ID, id);
        startActivity(intent);
    }

    @Override
    public void goToNoteListActivity() {
        startActivity(new Intent(this, NoteListActivity.class));
    }

    @Override
    public void onClick(View v) {
        presenter.confirmNote(
                titleEditText.getText().toString().trim(),
                contentEditText.getText().toString().trim()
        );
    }
}