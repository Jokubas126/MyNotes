package com.example.mynotes.view.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynotes.R;
import com.example.mynotes.model.util.ConversionUtil;

import java.util.Objects;

public class ImageFragment extends Fragment {

    private ImageView imageView;
    private Bitmap image;

    public ImageFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("note_image")) {
            image = ConversionUtil.stringUriToBitmap(
                    Objects.requireNonNull(getActivity()).getApplicationContext(),
                    bundle.getString("note_image")
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        if(image != null){
            imageView = view.findViewById(R.id.note_image_view);
            imageView.setImageBitmap(image);
        }
        return view;
    }

    public Bitmap getImage() {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        return drawable.getBitmap();
    }
}
