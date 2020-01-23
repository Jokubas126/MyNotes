package com.example.mynotes.noteedit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mynotes.R;
import com.example.mynotes.tasks.PlayAudioTask;
import com.example.mynotes.tasks.RecordAudioTask;

public class RecorderPopupWindow implements View.OnClickListener, View.OnTouchListener{

    private Context context;
    private View popupView;
    private Listener listener;

    private PlayAudioTask playTask;

    // for checking if the button to record is still pressed down
    private static boolean hold = false;

    private RecordAudioTask recordTask;

    public RecorderPopupWindow(Context context, View popupView, Listener listener){
        this.context = context;
        this.popupView = popupView;
        this.listener = listener;
        setupWindow();
    }

    public interface Listener {
        void recorderCallBack(String filePath);
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
                if (recordTask != null){
                    playTask = new PlayAudioTask(recordTask.getFilePath());
                    playTask.execute();
                }
                break;

            case R.id.stop_recording_button:
                if (playTask != null){
                    playTask.stopPlaying();
                }
                break;

            case R.id.save_recording_button:
                if (recordTask != null){
                    listener.recorderCallBack(recordTask.getFilePath());
                    Log.d("RecorderPopupWindow", "FILE SAVED WITH NAME: " + recordTask.getFilePath());
                }
                break;

            case R.id.recorder_background:
                listener.recorderCallBack(null);
                if (playTask != null)
                    playTask.stopPlaying();
                break;

            case R.id.player_card:
            case R.id.recorder_card_view:
                //made so when clicked on the card and not the button, it wouldn't dismiss the window
                Log.d("RecorderPopupWindow", "onClick: RECORDER CARD");
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
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
