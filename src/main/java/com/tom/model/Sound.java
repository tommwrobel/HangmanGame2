package com.tom.model;

public enum Sound {
    MAIN_MUSIC("/sound/Main_music.mp3"),
    CLICK("/sound/Click.mp3");

    private String filePath;

    Sound(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
