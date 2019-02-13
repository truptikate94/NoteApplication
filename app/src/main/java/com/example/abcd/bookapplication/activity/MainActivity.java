package com.example.abcd.bookapplication.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abcd.bookapplication.R;
import com.example.abcd.bookapplication.adapter.NoteAdapter;
import com.example.abcd.bookapplication.database.DatabaseHelper;
import com.example.abcd.bookapplication.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    TextView tvEmptyList;
    public List<Note> noteList;
    NoteAdapter mAdapter;
    DatabaseHelper db;

    Boolean doubleBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DatabaseHelper(this);
        //db.deleteTable();
        recyclerView = findViewById(R.id.recycler);
        noteList = new ArrayList<>();
        tvEmptyList = findViewById(R.id.tv_empty_list);


        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateNoteActivity.class);
                intent.putExtra("TAG","newNote");
                startActivityForResult(intent,1);
            }
        });





        getAllNotes();
       if(noteList.isEmpty())
       {
           tvEmptyList.setVisibility(View.VISIBLE);
       }


           mAdapter = new NoteAdapter(noteList,this);
           RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
           RecyclerView.LayoutManager test = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
           RecyclerView.LayoutManager gridLayout = new GridLayoutManager(getApplicationContext(),2);
           recyclerView.setLayoutManager(test);
           recyclerView.setAdapter(mAdapter);




       // getData();

    }

    @Override
    protected void onResume() {

        //noteList.clear();
       // noteList.addAll(db.getAllNotes());
        if (noteList.isEmpty()){

        }
        else {
            tvEmptyList.setVisibility(View.GONE);
        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == 1)
        {
            if(data.getStringExtra("TAG").equals("save"))
            {
                int id = data.getIntExtra("id",0);
                String notes = data.getStringExtra("notes");
                String title = data.getStringExtra("title");
                if(id == 0 )
                {
                    insertNote(notes,title);
                }
                else
                {
                    updateNote(id,notes,title);
                }
            }

        }
    }

    private void updateNote(int id, String notes, String title) {

        Note note = new Note();
        note.setId(id);
        note.setTitle(title);
        note.setNote(notes);
        db.updateNote(note);
        getAllNotes();
        mAdapter.notifyDataSetChanged();

    }

    private void insertNote(String notes, String title) {

        Note note = new Note();
        note.setTitle(title);
        note.setNote(notes);

        Long id = db.insertNotes(note);
        Note note1 = db.getNotes(id);
        noteList.add(note1);
        mAdapter.notifyDataSetChanged();
    }

    private void getAllNotes(){
        noteList.clear();
        noteList.addAll(db.getAllNotes());
       // mAdapter.notifyDataSetChanged();
    }



    @Override
    public void onBackPressed() {
        if(doubleBackPressed)
        {
            super.onBackPressed();
            return;
        }

        doubleBackPressed = true;
        Toast.makeText(this,"press again to exit.",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPressed = false;
            }
        },2000);


    }

    public void deleteNote(int id) {
        db.deleteNote(id);
        getAllNotes();
        mAdapter.notifyDataSetChanged();

    }
}
