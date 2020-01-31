package com.tom.controller;

import com.tom.model.Sound;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.*;

public class SoundController {
    private final Map<Sound, MediaPlayer> sounds;

    public SoundController() {
        sounds = new HashMap<>();
        for (Sound sound : Sound.values()) {
            Media media = new Media(sound.getFilePath());
            sounds.put(sound, new MediaPlayer(media));
        }
    }

    public void play(Sound sound, boolean loop) {
        MediaPlayer soundFile = this.sounds.get(sound);
        if (loop) {
            soundFile.setCycleCount(MediaPlayer.INDEFINITE);
        }
        soundFile.seek(soundFile.getStartTime());
        soundFile.play();
    }
}
