package com.example.abcd.bookapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.abcd.bookapplication.model.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public long insertNotes(Note notes)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COL_TITLE,notes.getTitle());
        values.put(Note.COL_NOTES,notes.getNote());
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
                cursor.getString(cursor.getColumnIndex(Note.COL_TITLE)),
                cursor.getString(cursor.getColumnIndex(Note.COL_NOTES)),
                cursor.getString(cursor.getColumnIndex(Note.COL_DATE)));

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
                note.setTitle(cursor.getString(cursor.getColumnIndex(Note.COL_TITLE)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COL_NOTES)));
                note.setDate(cursor.getString(cursor.getColumnIndex(Note.COL_DATE)));

                noteList.add(note);
            } while (cursor.moveToNext());

        }
        return noteList;
    }

    public int updateNote(Note note){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COL_TITLE,note.getTitle());
        values.put(Note.COL_NOTES,note.getNote());
       // values.put(Note.COL_DATE,"DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')");
        values.put(Note.COL_DATE,getTime());


        return db.update(Note.TABLE_NOTE,values,Note.COL_ID+"=?",new String[]{String.valueOf(note.getId())});

    }

    public void deleteNote(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NOTE,Note.COL_ID +"=?",new String[]{String.valueOf(id)});
        db.close();
    }


    public String getTime()
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

}
