package com.example.mynotes.model.util;

public class DBUtil {

    private DBUtil(){}

    //items related to the database
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "notes_db";
    public static final String TABLE_NAME = "notes";

    // column names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TEXT = "text";

}
