package com.tom.controller;

import com.tom.model.Sound;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

public class SoundController {

    private final Map<Sound, MediaPlayer> sounds;

    public SoundController() {
        sounds = new HashMap<>();
        for (Sound sound : Sound.values()) {
            Media media = new Media(this.getClass().getResource(sound.getFilePath()).toString());
            sounds.put(sound, new MediaPlayer(media));
        }
    }

    public void play(Sound sound, boolean loop) {
        MediaPlayer soundFile = this.sounds.get(sound);
        if (loop) {
            soundFile.setCycleCount(MediaPlayer.INDEFINITE);
        }
        soundFile.seek(soundFile.getStartTime());
        soundFile.setVolume(0.8);
        soundFile.play();
    }

    public void stop(Sound sound) {
        this.sounds.get(sound).stop();
    }
}
