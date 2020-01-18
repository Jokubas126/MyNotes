package com.example.mynotes.tasks;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class PlayAudioTask extends AsyncTask<Void, Void, Void> implements Runnable, MediaPlayer.OnPreparedListener {

    private MediaPlayer player;
    private String filePath;

    public PlayAudioTask(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        setupPlayer();
        return null;
    }

    private void setupPlayer() {
        player = new MediaPlayer();
        try {
            player.setDataSource(filePath);
            player.setOnPreparedListener(this);
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPlaying () {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void run() {
        if (player != null)
            player.start();
        else Log.d("Player", "run started but player is null");
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        player.start();
    }


}
