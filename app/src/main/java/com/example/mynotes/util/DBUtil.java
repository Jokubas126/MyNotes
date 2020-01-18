package com.example.mynotes.util;


public class DBUtil {

    private DBUtil(){}

    //items related to the database
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "notes_db";
    public static final String TABLE_NAME = "notes";

    // column names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TEXT = "text";
    public static final String KEY_IMAGE_PATH = "image";
    public static final String KEY_AUDIO_FILE_PATH = "audio";
    public static final String KEY_CHECKED = "checked";
}
