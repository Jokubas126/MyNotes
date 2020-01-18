package com.example.mynotes.notelist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.util.BundleExtraUtil;
import com.example.mynotes.notedetails.NoteDetailsActivity;
import com.example.mynotes.noteedit.NoteEditActivity;

import java.util.Objects;

public class NoteListFragment extends Fragment implements NoteListContract.View, View.OnClickListener {

    private Activity activity;
    private NoteListContract.Presenter presenter;

    public NoteListFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = Objects.requireNonNull(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        ImageButton menuButton = view.findViewById(R.id.menu_button);
        menuButton.setOnClickListener(this);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        presenter = new NoteListPresenter(activity, this, recyclerView);
        return view;
    }

    @Override
    public void goToNoteEditActivity() {
        startActivity(new Intent(activity, NoteEditActivity.class));
    }

    @Override
    public void goToNoteDetailsActivity(int id) {
        Intent intent = new Intent(activity, NoteDetailsActivity.class);
        intent.putExtra(BundleExtraUtil.KEY_NOTE_ID, id);
        startActivity(intent);
    }

    @Override
    public void setPresenter(NoteListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_note_floating_button:
                presenter.addNote("", "");
                Intent intent = new Intent(activity, NoteEditActivity.class);
                intent.putExtra(BundleExtraUtil.KEY_NOTE_ID, presenter.getNewNoteIndex());
                startActivity(intent);
                break;

            case R.id.menu_button:
                presenter.showPopupMenu(v);
                break;
        }
    }
}
