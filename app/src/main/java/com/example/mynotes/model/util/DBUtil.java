package com.example.mynotes.model.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class DBUtil {

    private DBUtil(){}

    //items related to the database
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "notes_db";
    public static final String TABLE_NAME = "notes";

    // column names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TEXT = "text";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_CHECKED = "checked";

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
