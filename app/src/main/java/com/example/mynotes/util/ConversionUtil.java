package com.example.mynotes.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;

public class ConversionUtil {

    public static Bitmap stringUriToBitmap(Context context, String stringUri){
        try {
            Uri imageUri = Uri.parse(stringUri);
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
        } catch (IOException e) {
            Log.d("Conversion", "stringUriToBitmap: making bitmap failed");
            return null;
        }
    }

}
