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

public class EditTextFragment extends Fragment {

    private TextView noteTextView;
    private String text;

    public EditTextFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("note_text")){
            text = bundle.getString("note_text");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_text, container, false);

        noteTextView = view.findViewById(R.id.content_edit_text);
        if (!text.equals("") && noteTextView != null){
            noteTextView.setText(text);
        }
        return view;
    }

    public String getString() {
        return noteTextView.getText().toString().trim();
    }
}
