package com.example.mynotes.presenters.tasks;

import android.media.MediaPlayer;
import android.os.AsyncTask;

import java.io.IOException;

public class PlayAudioTask extends AsyncTask {

    private MediaPlayer player;

    private String filePath;

    public PlayAudioTask(String filePath){
        this.filePath = filePath;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        player = new MediaPlayer();
        if (!player.isPlaying()){
            try {
                player.setDataSource(filePath);
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            stopPlaying();
        }
        return null;
    }

    public void stopPlaying(){
        if (player != null){
            player.stop();
            player.release();
            player = null;
        }
    }
}
