package com.example.mynotes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
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
    private NoteListActivityPresenter presenter;
    private CheckBox checkBox;
    //private static int checkboxVisibility = View.GONE;
    private static boolean checkboxChecked = false;

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
        holder.imageView.setImageBitmap(note.getImage());
    }

    public void deleteNotes(){
        for(Note note : noteList){
            if (note.isChecked())
                presenter.deleteNote(note.getId());
        }
    }

    /*public void changeCheckboxVisibility(int id){
        if (checkBox.getVisibility() == View.VISIBLE && id == R.id.select_notes_button){
            checkboxChecked = false;
            checkboxVisibility = View.GONE;
        } else{
            checkboxVisibility = View.VISIBLE;
        }
    }*/

    public void setAllNotesChecked(){
        for (Note note : noteList){
            note.setChecked(!note.isChecked());
        }
        checkboxChecked = !checkboxChecked;
        checkBox.setChecked(checkboxChecked);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView contentTextView;
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    Log.d("OnNoteRowClick", "onClick: " + "row is clicked");
                    int id = noteList.get(position).getId();
                    presenter.onNoteClicked(id);
                }
            });
            titleTextView = itemView.findViewById(R.id.title_text);
            contentTextView = itemView.findViewById(R.id.content_text);
            imageView = itemView.findViewById(R.id.note_image_view);
            checkBox = itemView.findViewById(R.id.item_checkbox);
            //checkBox.setVisibility(checkboxVisibility);
            checkBox.setChecked(checkboxChecked);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            noteList.get(position).setChecked(!noteList.get(position).isChecked());
        }
    }
}
