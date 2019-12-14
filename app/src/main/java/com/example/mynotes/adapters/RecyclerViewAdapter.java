package com.example.mynotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Note> noteList;

    public RecyclerViewAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.contentTextView.setText(note.getText());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titleTextView;
        public TextView contentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text);
            contentTextView = itemView.findViewById(R.id.content_text);
        }
    }
}
