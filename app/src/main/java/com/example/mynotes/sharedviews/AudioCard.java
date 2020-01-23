package com.example.mynotes.sharedviews;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mynotes.R;
import com.example.mynotes.tasks.PlayAudioTask;

public class AudioCard implements View.OnClickListener {

    public interface OnRemoveAudioListener{
        void onAudioRemove();
    }

    private EditText titleText;
    private OnRemoveAudioListener onRemoveListener;
    private View fragmentView;
    private String filePath;
    private String title;

    private PlayAudioTask playTask;

    public AudioCard(View fragmentView, String audioFilePath, String title) {
        this.fragmentView = fragmentView;
        this.filePath = audioFilePath;
        this.title = title;
        setupCard(false);
    }

    public AudioCard(View fragmentView, String audioFilePath, String title, OnRemoveAudioListener listener) {
        this.fragmentView = fragmentView;
        this.filePath = audioFilePath;
        this.title = title;
        this.onRemoveListener = listener;
        setupCard(true);
    }

    private void setupCard(boolean isEditable){
        titleText = fragmentView.findViewById(R.id.recording_title);
        titleText.setText(title);

        ImageButton playButton = fragmentView.findViewById(R.id.play_recording_button);
        ImageButton stopButton = fragmentView.findViewById(R.id.stop_recording_button);
        ImageButton deleteRecordingButton = fragmentView.findViewById(R.id.remove_recording_button);
        playButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        if (isEditable){
            deleteRecordingButton.setVisibility(View.VISIBLE);
            deleteRecordingButton.setOnClickListener(this);
            titleText.setFocusable(true);
        } else {
            titleText.setFocusable(false);
            deleteRecordingButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_recording_button:
                playTask = new PlayAudioTask(filePath);
                playTask.execute();
                break;

            case R.id.stop_recording_button:
                playTask.stopPlaying();
                break;

            case R.id.remove_recording_button:
                onRemoveListener.onAudioRemove();
                break;
        }
    }

    public String getTitle() {
        return titleText.getText().toString().trim();
    }
}
