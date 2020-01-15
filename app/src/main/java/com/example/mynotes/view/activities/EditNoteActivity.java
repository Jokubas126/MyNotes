package com.example.mynotes.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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

    private EditText titleEditText;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        new StyleSetup(this, getSupportActionBar());

        titleEditText = findViewById(R.id.title_edit_text);

        ImageButton confirmButton = findViewById(R.id.confirm_button);
        ImageButton galleryButton = findViewById(R.id.gallery_button);
        ImageButton recorderButton = findViewById(R.id.recorder_button);

        confirmButton.setOnClickListener(this);
        galleryButton.setOnClickListener(this);
        recorderButton.setOnClickListener(this);

        presenter = new EditNoteActivityPresenter(this, this);
        presenter.getInformation(this, getIntent().getExtras());
    }

    @Override
    public void displayInformation(String title) {
        titleEditText.setText(title);
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
                presenter.confirmNote(titleEditText.getText().toString().trim());
                break;

            case R.id.gallery_button:
                presenter.saveNoteState(titleEditText.getText().toString().trim());
                startActivityForResult(
                        presenter.getIntentForImage(),
                        presenter.getRequestCodeForImage()
                );
                break;

            case R.id.recorder_button:
                ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
                presenter.loadRecorder(this);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //getting an image selected from the gallery shown in the note
        if (requestCode == presenter.getRequestCodeForImage() && resultCode == Activity.RESULT_OK){
            presenter.loadImage(data);
            getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            presenter.loadNote(this);
        }
    }

    @Override
    public void showRecording() {
        //RelativeLayout parentLayout = (RelativeLayout) findViewById(R.id.edit_note_relative_layout);
        //getLayoutInflater().inflate(R.layout.audio_card_view, parentLayout);
    }
}
