package com.tom.controller;

import com.tom.model.Sound;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController {

    private MediaPlayer[] sound;

    public SoundController() {
        int numberOfSounds = Sound.values().length;
        sound = new MediaPlayer[numberOfSounds];

        for(int i = 0; i < numberOfSounds; i++) {
            Media media = new Media(this.getClass().getResource(Sound.values()[i].getSoundPath()).toString());
            sound[i] = new MediaPlayer(media);
        }
    }

    public void play(Sound sound, boolean repeat) {
        MediaPlayer soundFile = this.sound[sound.ordinal()];
        soundFile.setCycleCount(MediaPlayer.INDEFINITE);
        soundFile.seek(soundFile.getStartTime());
        soundFile.play();
    }

    public void play(Sound sound) {
        MediaPlayer soundFile = this.sound[sound.ordinal()];
        soundFile.seek(soundFile.getStartTime());
        soundFile.play();
    }
}
