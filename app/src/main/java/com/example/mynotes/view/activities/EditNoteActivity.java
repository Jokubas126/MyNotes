package com.example.mynotes.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

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
    private ImageView imageView;
    private ImageButton galleryButton;
    private ImageButton recorderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        new StyleSetup(this, getSupportActionBar());

        titleEditText = findViewById(R.id.title_edit_text);
        contentEditText = findViewById(R.id.content_edit_text);

        confirmButton = findViewById(R.id.confirm_button);
        galleryButton = findViewById(R.id.gallery_button);
        recorderButton = findViewById(R.id.recorder_button);

        imageView = findViewById(R.id.note_image_view);

        confirmButton.setOnClickListener(this);
        galleryButton.setOnClickListener(this);
        recorderButton.setOnClickListener(this);

        presenter = new EditNoteActivityPresenter(this, this);
        presenter.getInformation(getIntent().getExtras());
    }

    @Override
    public void displayInformation(String title, String content, Bitmap image) {
        titleEditText.setText(title);
        contentEditText.setText(content);
        imageView.setImageBitmap(image);
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
        switch (v.getId()){
            case R.id.confirm_button:
                presenter.confirmNote(
                        titleEditText.getText().toString().trim(),
                        contentEditText.getText().toString().trim()
                );
                break;

            case R.id.gallery_button:
                startActivityForResult(
                        presenter.getIntentForImage(),
                        presenter.getRequestCodeForImage()
                );
                break;

            case R.id.recorder_button:
                presenter.loadRecorder(this);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //
        if (requestCode == presenter.getRequestCodeForImage() && resultCode == Activity.RESULT_OK){
            presenter.loadImage(this, data);
            presenter.loadNote();
        }
    }
}
