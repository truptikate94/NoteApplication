package com.example.abcd.bookapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.abcd.bookapplication.R;
import com.example.abcd.bookapplication.activity.CreateNoteActivity;
import com.example.abcd.bookapplication.activity.MainActivity;
import com.example.abcd.bookapplication.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    public List<Note> noteList;
    public Context mcontext;

    public NoteAdapter(List<Note> noteList, Context mcontext) {
        this.noteList = noteList;
        this.mcontext = mcontext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeNote;
        TextView tvDate,tvNote;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeNote = itemView.findViewById(R.id.relative_note);
            tvNote = itemView.findViewById(R.id.tv_file_content);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final Note note = noteList.get(position);
        holder.tvNote.setText(note.getNote());
        holder.tvDate.setText(note.getDate());
        holder.relativeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext,CreateNoteActivity.class);
                intent.putExtra("TAG","editNote");
                intent.putExtra("contents",noteList.get(position).getNote());
                intent.putExtra("id",noteList.get(position).getId());
                ((Activity)mcontext).startActivityForResult(intent,1);

            }
        });

        holder.relativeNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mcontext);
                alertDialog.setMessage("do you want to delete this note?");
                alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)mcontext).deleteNote(noteList.get(position).getId());
                    }
                });
                alertDialog.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }


}
