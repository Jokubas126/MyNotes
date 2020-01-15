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
import com.example.mynotes.presenters.tasks.RecordAudioTask;

public class RecorderWindow implements View.OnClickListener, View.OnTouchListener{

    private Context context;
    private View popupView;
    private EditNoteActivityPresenter activityPresenter;

    // for checking if the button to record is still pressed down
    private static boolean hold = false;

    private RecordAudioTask recordTask;

    public RecorderWindow(Context context, View popupView, EditNoteActivityPresenter presenter){
        this.context = context;
        this.popupView = popupView;
        this.activityPresenter = presenter;
        setupWindow();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupWindow(){
        ImageButton recordButton = popupView.findViewById(R.id.record_button);
        ImageButton stopButton = popupView.findViewById(R.id.stop_recording_button);
        ImageButton playButton = popupView.findViewById(R.id.play_recording_button);
        ImageButton saveButton = popupView.findViewById(R.id.save_recording_button);

        ConstraintLayout layout = popupView.findViewById(R.id.recorder_background);
        CardView recordCardView = popupView.findViewById(R.id.recorder_card_view);
        CardView playerCardView = popupView.findViewById(R.id.player_card);

        recordButton.setOnTouchListener(this);
        stopButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        layout.setOnClickListener(this);
        recordCardView.setOnClickListener(this);
        playerCardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_recording_button:
                if (recordTask != null)
                    recordTask.startPlaying();
                break;

            case R.id.stop_recording_button:
                if (recordTask != null){
                    recordTask.stopPlaying();
                }
                break;

            case R.id.save_recording_button:
                if (recordTask != null){
                    activityPresenter.saveRecording(recordTask.getFile());
                    Log.d("RecorderWindow", "FILE SAVED WITH NAME: " + recordTask.getFile().getPath());
                }
                break;

            case R.id.recorder_background:
                activityPresenter.dismissPopupWindow();
                if (recordTask != null)
                    recordTask.stopPlaying();
                break;

            case R.id.recorder_card_view:
                //made so when clicked on the card and not the button, it wouldn't dismiss the window
                Log.d("RecorderWindow", "onClick: RECORDER CARD");
                break;

            case R.id.player_card:
                //made so when clicked on the card and not the button, it wouldn't dismiss the window
                Log.d("RecorderWindow", "onClick: PLAYER CARD");
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                recordTask = new RecordAudioTask();
                recordTask.setupRecorder(context);
                if(!hold){
                    recordTask.setHold(true);
                    recordTask.execute();
                    v.setScaleX(0.8f);
                    v.setScaleY(0.8f);
                }
                break;

            case MotionEvent.ACTION_UP:
                recordTask.stopRecording();
                Log.d("Recorder", "STOPPED");
                hold = false;
                recordTask.setHold(false);
                v.setScaleX(1.0f);
                v.setScaleY(1.0f);
        }
        return true;
    }
}
