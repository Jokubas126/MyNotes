package com.example.mynotes.view.popup_window_controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mynotes.R;
import com.example.mynotes.presenters.EditNoteActivityPresenter;

import java.util.Timer;
import java.util.TimerTask;

public class RecorderWindow implements View.OnClickListener, View.OnTouchListener {

    Context context;
    View popupView;
    EditNoteActivityPresenter presenter;

    ConstraintLayout layout;
    ImageButton recordButton;
    CardView cardView;

    public RecorderWindow(){}

    public RecorderWindow(Context context, View popupView, EditNoteActivityPresenter presenter){
        this.context = context;
        this.popupView = popupView;
        this.presenter = presenter;
        setupWindow();
    }

    public void setupWindow(){
        recordButton = popupView.findViewById(R.id.record_button);
        layout = popupView.findViewById(R.id.recorder_background);
        cardView = popupView.findViewById(R.id.recorder_card_view);
        recordButton.setOnTouchListener(this);
        layout.setOnClickListener(this);
        cardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recorder_background:
                presenter.dismissPopupWindow();
                break;

            case R.id.recorder_card_view:
                Log.d("RecorderWindow", "onClick: CARD");
                break;
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPopupView(View popupView) {
        this.popupView = popupView;
    }

    public void setPresenter(EditNoteActivityPresenter presenter) {
        this.presenter = presenter;
    }

    private static boolean hold = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                if(!hold){
                    hold = true;
                    new RecordTask().execute();
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d("Recorder", "STOPPED");
                hold = false;

        }
        return true;
    }

    private static class RecordTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            while (hold) {
                record();
            }
            return null;
        }

        private void record(){
            Log.d("Recorder", "recording...");
        }
    }
}
