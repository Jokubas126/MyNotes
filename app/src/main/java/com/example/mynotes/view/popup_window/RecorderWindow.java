package com.example.mynotes.view.popup_window;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mynotes.R;
import com.example.mynotes.presenters.EditNoteActivityPresenter;
import com.example.mynotes.presenters.tasks.RecordTask;

public class RecorderWindow implements View.OnClickListener, View.OnTouchListener {

    private Context context;
    private View popupView;
    private EditNoteActivityPresenter activityPresenter;

    // for checking if the button to record is still pressed down
    private static boolean hold = false;

    private RecordTask recordTask;

    public RecorderWindow(Context context, View popupView, EditNoteActivityPresenter presenter){
        this.context = context;
        this.popupView = popupView;
        this.activityPresenter = presenter;
        setupWindow();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupWindow(){
        ImageButton recordButton = popupView.findViewById(R.id.record_button);
        ConstraintLayout layout = popupView.findViewById(R.id.recorder_background);
        CardView cardView = popupView.findViewById(R.id.recorder_card_view);
        recordButton.setOnTouchListener(this);
        layout.setOnClickListener(this);
        cardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recorder_background:
                activityPresenter.dismissPopupWindow();
                if (recordTask != null)
                    recordTask.stopPlaying();
                break;

            case R.id.recorder_card_view:
                //made so when clicked on the card and not the button, it wouldn't dismiss the window
                Log.d("RecorderWindow", "onClick: CARD");
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                recordTask = new RecordTask();
                recordTask.setupRecorder(context);
                if(!hold){
                    recordTask.setHold(true);
                    recordTask.execute();
                }
                break;

            case MotionEvent.ACTION_UP:
                recordTask.stopRecording();
                Log.d("Recorder", "STOPPED");
                hold = false;
                recordTask.setHold(false);
        }
        return true;
    }
}
