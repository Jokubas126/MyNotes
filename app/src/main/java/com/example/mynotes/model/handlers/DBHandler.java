package com.example.mynotes.model.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.util.DBUtility;

public class DBHandler extends SQLiteOpenHelper {


    public DBHandler(Context context) {
        super(context, DBUtility.DATABASE_NAME, null, DBUtility.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTE_TABLE = "CREATE TABLE "
                + DBUtility.TABLE_NAME + "("
                + DBUtility.KEY_ID + "INTEGER PRIMARY KEY,"
                + DBUtility.KEY_TITLE + " TEXT,"
                + DBUtility.KEY_TEXT + " TEXT" + ")";
        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{DBUtility.DATABASE_NAME});
        onCreate(db);
    }

    //-----------CRUD-------------//

    public void addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBUtility.KEY_TITLE, note.getTitle());


    }
}
