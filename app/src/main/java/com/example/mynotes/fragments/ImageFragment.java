package com.example.mynotes.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.util.BundleExtraUtil;
import com.example.mynotes.model.util.ConversionUtil;
import com.google.gson.Gson;

import java.util.Objects;

public class ImageFragment extends Fragment {

    private Bitmap image;

    public ImageFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(BundleExtraUtil.KEY_NOTE_OBJECT)) {
            String json = bundle.getString(BundleExtraUtil.KEY_NOTE_OBJECT);
            image = ConversionUtil.stringUriToBitmap(
                    Objects.requireNonNull(getActivity()).getApplicationContext(),
                    new Gson().fromJson(json, Note.class).getImageUriString()
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = view.findViewById(R.id.note_image_view);
        if(image != null){
            imageView.setImageBitmap(image);
        }
        return view;
    }
}
