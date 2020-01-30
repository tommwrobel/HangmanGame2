package com.tom.model;

public enum WordCategory {
    ANIMALS("/data/animals.txt", "ANIMALS"),
    PEOPLES("/data/places.txt", "PLACES"),
    SCIENCE("/data/science.txt", "SCIENCE");

    private String dataFile;
    private String displayName;

    WordCategory(String dataFile, String displayName) {
        this.dataFile = dataFile;
        this.displayName = displayName;
    }

    public String getDataFile() {
        return dataFile;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
