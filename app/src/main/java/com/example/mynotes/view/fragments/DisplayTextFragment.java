package com.example.mynotes.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynotes.R;

public class DisplayTextFragment extends Fragment {

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

        if(titleString != null){
            titleView.setText(titleString);
        }
        if (contentString != null){
            contentView.setText(contentString);
        }

        return view;
    }

    public String getTitleString() {
        return titleString;
    }

    public String getContentString() {
        return contentString;
    }
}
