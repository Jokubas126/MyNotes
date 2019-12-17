package com.example.mynotes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.model.data.Note;
import com.example.mynotes.presenters.NoteListActivityPresenter;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Note> noteList;
    NoteListActivityPresenter presenter;
    private CheckBox checkBox;

    public RecyclerViewAdapter(Context context, List<Note> noteList, NoteListActivityPresenter presenter) {
        this.context = context;
        this.noteList = noteList;
        this.presenter = presenter;
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
        holder.contentTextView.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleTextView;
        public TextView contentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.title_text);
            contentTextView = itemView.findViewById(R.id.content_text);
            checkBox = itemView.findViewById(R.id.item_checkbox);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (v.getId()){
                case R.layout.note_list_row:
                    int id = noteList.get(position).getId();
                    presenter.onNoteClicked(id);
                    break;

                case R.id.item_checkbox:
                    if (noteList.get(position).isChecked()){
                        noteList.get(position).setChecked(false);
                        Log.d("Checkbox", "onClick: " + noteList.get(position).getTitle() + " " + noteList.get(position).isChecked());
                    }
                    else {
                        noteList.get(position).setChecked(true);
                        Log.d("Checkbox", "onClick: " + noteList.get(position).getTitle() + " " + noteList.get(position).isChecked());
                    }

                    break;
            }

        }
    }
}
