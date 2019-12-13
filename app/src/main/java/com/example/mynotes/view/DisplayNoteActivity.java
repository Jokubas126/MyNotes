package com.example.mynotes.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.presenters.DisplayNoteActivityPresenter;

public class DisplayNoteActivity extends AppCompatActivity
        implements DisplayNoteActivityPresenter.DisplayNoteActivityView {

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

        presenter.getInformation(getIntent().getExtras());
    }

    @Override
    public void displayInformation(String title, String content) {
        titleTextView.setText(title);
        contentTextView.setText(content);
    }
}
