package com.example.abcd.bookapplication.model;

public class Note {

    public static final String TABLE_NOTE = "notes";

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_NOTES = "note";
    public static final String COL_DATE = "date";

    public static final String CREATE_TABLE= "CREATE TABLE "+TABLE_NOTE+"("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_TITLE+" TEXT,"+COL_NOTES+" TEXT,"+COL_DATE+" DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')))";

    public static final String[] COLS={COL_ID,COL_TITLE,COL_NOTES,COL_DATE};
    private  int id;
    private String title;
    private String note;
    private String date;

    public Note(){}

    public Note(int id,String title,String note, String date) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {

        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
