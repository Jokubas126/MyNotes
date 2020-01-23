package com.example.mynotes.model.data;

public class Note {

    private int id;
    private String title;
    private String content;
    private String audioFilePath;
    private String audioFileTitle = "default";

    private String imageUriString;
    private boolean checked;

    public Note(){}

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUriString() {
        return imageUriString;
    }

    public void setImageUriString(String imageUriString) {
        this.imageUriString = imageUriString;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public String getAudioFileTitle() {
        return audioFileTitle;
    }

    public void setAudioFileTitle(String audioFileTitle) {
        if (audioFileTitle != null)
            this.audioFileTitle = audioFileTitle;
        else this.audioFileTitle = "default";
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
