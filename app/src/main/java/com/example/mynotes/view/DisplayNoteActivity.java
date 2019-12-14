package com.example.mynotes.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.presenters.DisplayNoteActivityPresenter;

public class DisplayNoteActivity extends AppCompatActivity
        implements DisplayNoteActivityPresenter.DisplayNoteActivityView, View.OnClickListener {

    TextView contentTextView;
    TextView titleTextView;

    DisplayNoteActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        presenter = new DisplayNoteActivityPresenter(this);

        titleTextView = findViewById(R.id.title_text_view);
        contentTextView = findViewById(R.id.content_text_view);
        titleTextView.setOnClickListener(this);
        contentTextView.setOnClickListener(this);

        presenter.getInformation(getIntent().getExtras());
    }

    @Override
    public void displayInformation(String title, String content) {
        titleTextView.setText(title);
        contentTextView.setText(content);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(BundleExtraUtil.NOTE_TITLE_TEXT, titleTextView.getText());
        intent.putExtra(BundleExtraUtil.NOTE_CONTENT_TEXT, contentTextView.getText());
        startActivity(intent);
    }
}
