package com.example.abcd.bookapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.abcd.bookapplication.model.Note;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE = "notedb";
    public static final int DATABASE_VERSION = 1;
    Context mContext;
    public DatabaseHelper(Context context) {

        super(context, DATABASE, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Note.TABLE_NOTE);
        onCreate(db);
    }

    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+Note.TABLE_NOTE);
        onCreate(db);
    }

    public long insertNotes(String notes)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COL_NOTES,notes);
        Long id = db.insert(Note.TABLE_NOTE,null,values);
        db.close();
        return id;
    }

    public Note getNotes(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NOTE,Note.COLS,Note.COL_ID+"=?",new String[]{String.valueOf(id)},null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(cursor.getInt(cursor.getColumnIndex(Note.COL_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COL_NOTES)),cursor.getString(cursor.getColumnIndex(Note.COL_DATE)));

        return note;
    }

    public List<Note> getAllNotes(){

        List<Note> noteList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+Note.TABLE_NOTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COL_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COL_NOTES)));
                note.setDate(cursor.getString(cursor.getColumnIndex(Note.COL_DATE)));

                noteList.add(note);
            } while (cursor.moveToNext());

        }
        return noteList;
    }

    /*public int updateNote(Note note) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(Note.COLUMN_NOTE, note.getNote());

    // updating row
    return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
            new String[]{String.valueOf(note.getId())});
}*/

    public int updateNote(int id,String notes){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COL_NOTES,notes);

        return db.update(Note.TABLE_NOTE,values,Note.COL_ID+"=?",new String[]{String.valueOf(id)});

    }

}
