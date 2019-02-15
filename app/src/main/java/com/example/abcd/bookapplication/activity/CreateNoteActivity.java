package com.example.abcd.bookapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abcd.bookapplication.R;
import com.example.abcd.bookapplication.database.DatabaseHelper;

public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtNotes,edtTitle;
    String strNoteTitle,strNoteDis;
    Button btnSave;
    int updateID;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        edtTitle = findViewById(R.id.edt_title);
        edtNotes = findViewById(R.id.edt_notes);
        btnSave = findViewById(R.id.btn_save);
        db = new DatabaseHelper(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        String TAG = getIntent().getStringExtra("TAG");

        updateID = getIntent().getIntExtra("id",0);
        if(TAG.equals("editNote"))
        {
            edtTitle.setText(getIntent().getStringExtra("title"));
            edtNotes.setText(getIntent().getStringExtra("contents"));
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_save:
                strNoteTitle = edtTitle.getText().toString().trim();
                strNoteDis = edtNotes.getText().toString().trim();
                if(isValid())
                    saveNote();
                /*else
                    Toast.makeText(this,"empty notes",Toast.LENGTH_SHORT).show();*/

                break;

        }

    }

    private boolean isValid() {
        if(strNoteTitle.isEmpty() || strNoteTitle == "")
        {
            Toast.makeText(this,"give title to notes",Toast.LENGTH_SHORT).show();
            return false;
        }else if (strNoteDis.isEmpty() || strNoteDis =="")
        {
            Toast.makeText(this,"empty notes",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void cancelNote(String message) {

        Intent intent = new Intent();
        intent.putExtra("TAG","cancel");
        intent.putExtra("message",message);
        setResult(1,intent);
        finish();
    }

    private void saveNote() {

        Intent intent = new Intent();
        intent.putExtra("title",strNoteTitle);
        intent.putExtra("notes",strNoteDis);
        intent.putExtra("id",updateID);
        intent.putExtra("TAG","save");
        setResult(1,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        strNoteTitle = edtTitle.getText().toString().trim();
        strNoteDis = edtNotes.getText().toString().trim();
        if((strNoteTitle.isEmpty() || strNoteTitle == "")&& (strNoteDis.isEmpty() || strNoteDis ==""))
        {
            cancelNote("cannot save empty notes");
            super.onBackPressed();
        }
        else
        {
            final AlertDialog.Builder discardDialog = new AlertDialog.Builder(this,R.style.CustomDialogTheme);
            discardDialog.setMessage("do you want to save this notes?");
            discardDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveNote();
                }
            });
            discardDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    discardDialog.setCancelable(true);
                    cancelNote("discard notes");
                }
            });

            discardDialog.show();
        }

    }
}
