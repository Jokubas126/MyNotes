package com.example.mynotes.notelist;

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
import com.example.mynotes.util.ConversionUtil;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private Context context;
    private List<Note> noteList;
    private NoteListContract.Presenter presenter;
    private CheckBox checkBox;
    private static boolean checkboxChecked = false;

    NoteListAdapter(Context context, List<Note> noteList, NoteListContract.Presenter presenter) {
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
        if (note.getImageUriString() != null)
            holder.imageView.setImageBitmap(ConversionUtil.stringUriToBitmap(context, note.getImageUriString()));

        if(note.getAudioFilePath() != null)
            holder.soundWaves.setVisibility(View.VISIBLE);
        else holder.soundWaves.setVisibility(View.GONE);
    }

    void deleteNotes(){
        for(Note note : noteList){
            if (note.isChecked())
                presenter.deleteNote(note.getId());
        }
    }

    void setAllNotesChecked(){
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

        private TextView titleTextView;
        private TextView contentTextView;
        private ImageView imageView;
        private ImageView soundWaves;

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
            soundWaves = itemView.findViewById(R.id.sound_wave);
            checkBox = itemView.findViewById(R.id.item_checkbox);
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
