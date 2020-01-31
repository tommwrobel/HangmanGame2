package com.tom.model;

public enum Sound {
    MAIN_MUSIC("/sound/Main_music.mp3"),
    WIN_MUSIC("/sound/Click.mp3"),
    LOOSE_MUSIC("/sound/Click.mp3"),
    CLICK("/sound/Click.mp3"),
    WRONG_ANSWER("/sound/Click.mp3"),
    GOOD_ANSWER("/sound/Click.mp3");

    private final String filePath;

    Sound(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.getClass().getResource(filePath).toString();
    }

}