package com.example.mynotes.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.presenters.DisplayNoteActivityPresenter;
import com.example.mynotes.view.StyleSetup;

public class DisplayNoteActivity extends AppCompatActivity
        implements DisplayNoteActivityPresenter.DisplayNoteActivityView, View.OnClickListener {

    private TextView contentTextView;
    private TextView titleTextView;
    private ImageView imageView;
    private ImageButton backButton;

    private DisplayNoteActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);
        new StyleSetup(this, getSupportActionBar());

        presenter = new DisplayNoteActivityPresenter(this, this);

        titleTextView = findViewById(R.id.title_text_view);
        contentTextView = findViewById(R.id.content_text_view);
        imageView = findViewById(R.id.note_image_view);
        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(this);
        titleTextView.setOnClickListener(this);
        contentTextView.setOnClickListener(this);

        presenter.getInformation(getIntent().getExtras());
    }

    @Override
    public void displayInformation(String title, String content, Bitmap image) {
        titleTextView.setText(title);
        contentTextView.setText(content);
        imageView.setImageBitmap(image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_text_view:
            case R.id.content_text_view:
                goToEditNoteActivity();
                break;

            case R.id.back_button:
                startActivity(new Intent(this, NoteListActivity.class));
                break;
        }
    }

    private void goToEditNoteActivity(){
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(BundleExtraUtil.KEY_NOTE_ID, presenter.getNoteId());
        startActivity(intent);
    }
}
