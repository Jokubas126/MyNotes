package com.example.mynotes.model.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.model.util.DBUtil;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context) {
        super(context, DBUtil.DATABASE_NAME, null, DBUtil.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTE_TABLE = "CREATE TABLE " + DBUtil.TABLE_NAME + "("
                + DBUtil.KEY_ID + " INTEGER PRIMARY KEY,"
                + DBUtil.KEY_TITLE + " TEXT,"
                + DBUtil.KEY_TEXT + " TEXT" + ")";
        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{DBUtil.DATABASE_NAME});
        onCreate(db);
    }

    //-----------CRUD-------------//

    public void addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBUtil.KEY_TITLE, note.getTitle());
        values.put(DBUtil.KEY_TEXT, note.getContent());

        db.insert(DBUtil.TABLE_NAME, null, values);
        Log.d(TAG, "addNote: note added to the database" );
        db.close();
    }

    public Note getNote(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DBUtil.TABLE_NAME,
                new String[]{DBUtil.KEY_ID, DBUtil.KEY_TITLE, DBUtil.KEY_TEXT},
                DBUtil.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Note note = new Note();
        note.setId(Integer.parseInt(cursor.getString(0)));
        note.setTitle(cursor.getString(1));
        note.setText(cursor.getString(2));
        return note;
    }

    public List<Note> getNoteList(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> list = new ArrayList<>();

        String selectAll = "SELECT * FROM " + DBUtil.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()){
            do {
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setText(cursor.getString(2));
                list.add(note);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int updateNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBUtil.KEY_TITLE, note.getTitle());
        values.put(DBUtil.KEY_TEXT, note.getContent());

        return db.update(DBUtil.TABLE_NAME, values, DBUtil.KEY_ID + "=?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBUtil.TABLE_NAME, DBUtil.KEY_ID + "=?",
                new String[]{String.valueOf(note.getId())});
    }

    public int getCount(){
        String countQuery = "SELECT * FROM " + DBUtil.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}
