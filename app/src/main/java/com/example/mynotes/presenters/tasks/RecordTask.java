package com.example.mynotes.presenters.tasks;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import static android.media.MediaRecorder.AudioEncoder.AAC_ELD;
import static android.media.MediaRecorder.AudioSource.MIC;
import static android.media.MediaRecorder.OutputFormat.THREE_GPP;

public class RecordTask extends AsyncTask<Void, Void, Void> {

    private MediaRecorder recorder;
    private MediaPlayer player;

    private File file;

    private boolean hold;

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        startRecording(hold);
        return null;
    }

    public void setupRecorder(Context context){
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

        recorder.setAudioEncoder(AAC_ELD);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.d("Recorder", "PREPARATION FAILED");
        }
    }

    //-----------RECORDER TASKS------------------

    private void startRecording(boolean hold){
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

    public void stopRecording(){
        if (recorder != null){
            //recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    //------------PLAYER TASKS------------------

    public void startPlaying(){
        player = new MediaPlayer();
        try {
            player.setDataSource(String.valueOf(file));
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e("PLAYER", "prepare() failed");
        }
    }

    public void stopPlaying(){
        if(player != null){
            player.release();
            player = null;
        }
    }
}
