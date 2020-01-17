package com.example.mynotes.recorder;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;

import com.example.mynotes.R;
import com.example.mynotes.tasks.PlayAudioTask;

public class AudioCard implements View.OnClickListener {

    private Context context;
    private CardView parentView;
    private String filePath;

    public AudioCard(Context context, CardView parentView, String audioFilePath) {
        this.context = context;
        this.parentView = parentView;
        this.filePath = audioFilePath;
        setupCard();
    }

    private void setupCard(){
        ImageButton playButton = parentView.findViewById(R.id.play_recording_button);
        playButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new PlayAudioTask(filePath);
    }
}
