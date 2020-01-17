package com.example.mynotes.notedetails;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynotes.R;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.noteedit.NoteEditActivity;

import java.util.Objects;

public class NoteDetailsFragment extends Fragment
        implements View.OnClickListener, NoteDetailsContract.View {

    private Activity activity;

    private NoteDetailsContract.Presenter presenter;

    private TextView titleView;
    private TextView contentView;
    private ImageView imageView;

    public NoteDetailsFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = Objects.requireNonNull(getActivity());
        presenter = new NoteDetailsPresenter(activity, this, activity.getIntent().getExtras());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_details, container, false);

        titleView = view.findViewById(R.id.title_text_view);
        contentView = view.findViewById(R.id.content_text_view);
        imageView = view.findViewById(R.id.note_image_view);

        presenter.loadInformation();

        titleView.setOnClickListener(this);
        contentView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.content_text_view:
            case R.id.title_text_view:
                goToEditNoteActivity();
                break;
        }
    }

    @Override
    public void goToEditNoteActivity() {
        Intent intent = new Intent(activity, NoteEditActivity.class);
        intent.putExtra(BundleExtraUtil.KEY_NOTE_ID, presenter.getNoteId());
        startActivity(intent);
    }

    @Override
    public void setViewInformation(String title, String content, Bitmap image) {
        titleView.setText(title);
        contentView.setText(content);
        if(image != null){
            imageView.setImageBitmap(image);
        }
    }

    @Override
    public void setPresenter(NoteDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
