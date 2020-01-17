package com.example.mynotes.noteedit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mynotes.R;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.notedetails.NoteDetailsActivity;

import java.util.Objects;

public class NoteEditFragment extends Fragment implements NoteEditContract.View, View.OnClickListener {

    private Activity activity;
    private NoteEditContract.Presenter presenter;

    private EditText titleView;
    private EditText contentView;
    private ImageView imageView;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    public NoteEditFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = Objects.requireNonNull(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_text, container, false);

        titleView = view.findViewById(R.id.title_edit_text);
        contentView = view.findViewById(R.id.content_edit_text);
        imageView = view.findViewById(R.id.note_image_view);

        ImageButton confirmButton = view.findViewById(R.id.confirm_button);
        ImageButton galleryButton = view.findViewById(R.id.gallery_button);
        ImageButton recorderButton = view.findViewById(R.id.recorder_button);
        galleryButton.setOnClickListener(this);
        recorderButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);

        presenter = new NoteEditPresenter(activity, this, activity.getIntent().getExtras());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gallery_button:
                presenter.saveNoteState(
                        titleView.getText().toString().trim(),
                        contentView.getText().toString().trim()
                );
                startActivityForResult(presenter.getIntentForImage(), presenter.getRequestCodeForImage());
                break;

            case R.id.recorder_button:
                ActivityCompat.requestPermissions(activity, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
                presenter.loadRecorder(activity);
                break;

            case R.id.confirm_button:
                presenter.saveNoteState(
                        titleView.getText().toString().trim(),
                        contentView.getText().toString().trim()
                );
                goToDetailsActivity();
                break;
        }
    }

    @Override
    public void displayInformation(String title, String content, Bitmap image) {
        titleView.setText(title);
        contentView.setText(content);
        imageView.setImageBitmap(image);
    }

    @Override
    public void goToDetailsActivity() {
        Intent intent = new Intent(activity, NoteDetailsActivity.class);
        intent.putExtra(BundleExtraUtil.KEY_NOTE_ID, presenter.getId());
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToRecordAccepted ) activity.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == presenter.getRequestCodeForImage() && resultCode == Activity.RESULT_OK){
            if (data != null) {
                Uri dataUri = data.getData();
                activity.getContentResolver().takePersistableUriPermission(
                        Objects.requireNonNull(dataUri),
                        Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                presenter.addImage(data);
            }
        }
    }

    @Override
    public void showRecording() {
        RelativeLayout parentLayout = activity.findViewById(R.id.edit_note_container);
        activity.getLayoutInflater().inflate(R.layout.audio_card_view, parentLayout);
    }

    @Override
    public void setPresenter(NoteEditContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
