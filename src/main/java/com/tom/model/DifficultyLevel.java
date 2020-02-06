package com.tom.model;

public enum DifficultyLevel {
    EASY(3,5, 40),
    MEDIUM(6, 7, 30),
    HARD(8,9, 20);

    private int minWordLength;
    private int maxWordLength;
    private int roundTime;

    DifficultyLevel(int minWordLength, int maxWordLength, int roundTime) {
        this.maxWordLength = maxWordLength;
        this.minWordLength = minWordLength;
        this.roundTime = roundTime;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }

    public int getMinWordLength() {
        return minWordLength;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public String getDisplayName() {
        return this.name();
    }
}
