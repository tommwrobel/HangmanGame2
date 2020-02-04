package com.tom.model;

public enum DifficultyLevel {
    EASY(10,45),
    MEDIUM(8, 30),
    HARD(6, 15);

    private int chances;
    private int roundTime;

    DifficultyLevel(int chances, int roundTime) {
        this.chances = chances;
        this.roundTime = roundTime;
    }

    public int getChances() {
        return chances;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public String getDisplayName() {
        return this.name();
    }
}
