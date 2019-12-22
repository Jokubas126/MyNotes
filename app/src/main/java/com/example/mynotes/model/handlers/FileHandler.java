package com.example.mynotes.model.handlers;

import android.content.Intent;
import android.provider.MediaStore;

public class FileHandler{

    public Intent getIntentForImage(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        pickIntent.setDataAndType(null, "image/*"); //make to setType(string) in case it doesn't work

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        return chooserIntent;
    }
}