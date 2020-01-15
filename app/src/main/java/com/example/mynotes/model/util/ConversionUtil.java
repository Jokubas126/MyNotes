package com.example.mynotes.model.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContentResolverCompat;

import java.io.IOException;

public class ConversionUtil {

    public static Bitmap stringUriToBitmap(Context context, String stringUri){
        Uri imageUri = Uri.parse(stringUri);
        try {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
        } catch (IOException e) {
            Log.d("Conversion", "stringUriToBitmap: making bitmap failed");
        }
        return null;
    }

}
