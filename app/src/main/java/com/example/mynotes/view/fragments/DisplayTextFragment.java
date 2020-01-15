package com.example.mynotes.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynotes.R;
import com.example.mynotes.view.activities.EditNoteActivity;

public class DisplayTextFragment extends Fragment implements View.OnClickListener {

    private String titleString;
    private String contentString;

    public DisplayTextFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null){
            if (bundle.containsKey("title_text")){
                titleString = bundle.getString("title_text");
            } else titleString = "";
            if (bundle.containsKey("content_text")){
                contentString = bundle.getString("content_text");
            } else contentString = "";
        } else {
            titleString = "";
            contentString = "";
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_note_text, container, false);

        TextView titleView = view.findViewById(R.id.title_text_view);
        TextView contentView = view.findViewById(R.id.content_text_view);

        view.setOnClickListener(this);
        titleView.setOnClickListener(this);
        contentView.setOnClickListener(this);


        if(titleString != null){
            titleView.setText(titleString);
        }
        if (contentString != null){
            contentView.setText(contentString);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.display_note_text_fragment:
            case R.id.content_text_view:
            case R.id.title_text_view:
                Log.d("DisplayTextFragment", "onClick: go to edit note activity");
                Intent intent = new Intent(getActivity(), EditNoteActivity.class);

                break;
        }
    }
}
