package com.example.mynotes.view.popup_window_controllers;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mynotes.R;
import com.example.mynotes.presenters.EditNoteActivityPresenter;

import java.io.File;
import java.io.IOException;

import static android.media.MediaRecorder.AudioSource.DEFAULT;
import static android.media.MediaRecorder.AudioSource.MIC;
import static android.media.MediaRecorder.OutputFormat.THREE_GPP;

public class RecorderWindow implements View.OnClickListener, View.OnTouchListener {

    private Context context;
    private View popupView;
    private EditNoteActivityPresenter presenter;

    //views for interaction
    private ConstraintLayout layout;
    private ImageButton recordButton;
    private CardView cardView;

    // for checking if the button to record is still pressed down
    private static boolean hold = false;

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



    public void setContext(Context context) {
        this.context = context;
    }

    public void setPopupView(View popupView) {
        this.popupView = popupView;
    }

    public void setPresenter(EditNoteActivityPresenter presenter) {
        this.presenter = presenter;
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

    private RecordTask recordTask;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                recordTask = new RecordTask();
                recordTask.setupRecorder(context);
                if(!hold){
                    hold = true;
                    recordTask.execute();
                }
                break;

            case MotionEvent.ACTION_UP:
                recordTask.stopRecording();
                Log.d("Recorder", "STOPPED");
                hold = false;
        }
        return true;
    }

    //----------------------------------------------------
//-----------------------RECORDER----------------------------------
    //----------------------------------------------------

    private static class RecordTask extends AsyncTask<Void, Void, Void> {

        private MediaRecorder recorder;

        private Context context;
        private File file;

        @Override
        protected Void doInBackground(Void... arg0) {
            record(hold);
            return null;
        }

        synchronized private void record(boolean hold){
            if (hold){
                if (recorder != null){
                    try {
                        recorder.start();
                    }catch (Exception e){
                        Log.d("Recorder", "RECORDING FAILED");
                    }
                }
            }
        }

        private void stopRecording(){
            recorder.stop();
            recorder.release();
            recorder = null;

            //play when stopped
            MediaPlayer player = new MediaPlayer();
            try {
                player.setDataSource(String.valueOf(file));
                player.prepare();
                player.start();
            } catch (IOException e) {
                Log.e("PLAYER", "prepare() failed");
            }
        }

        private void setupRecorder(Context context){
            this.context = context;
            file = new File(context.getCacheDir(), "raw");

            recorder = new MediaRecorder();
            recorder.setAudioSource(MIC);
            recorder.setOutputFormat(THREE_GPP);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d("Recorder", "DOING WITH FILE");
                recorder.setOutputFile(file);
            } else {
                Log.d("Recorder", "DOING WITH PATH");
                recorder.setOutputFile(context.getCacheDir() + "folder");
            }

            recorder.setAudioEncoder(DEFAULT);
            try {
                recorder.prepare();
            } catch (IOException e) {
                Log.d("Recorder", "PREPARATION FAILED");
            }
        }
    }
}
