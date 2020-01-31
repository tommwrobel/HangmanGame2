package com.tom.model;


public enum WordCategory {
    ANIMALS("/data/animals.txt"),
    COUNTRIES("/data/countries.txt"),
    SCIENCE("/data/science.txt");

    private final String dataFile;

    WordCategory(String dataFile) {
        this.dataFile = dataFile;
    }

    public String getDataFile() {
        return dataFile;
    }

    public String getDisplayName() {
        return this.name();
    }

    public static WordCategory getNextInOrder(WordCategory category) {
        return category.ordinal() == values().length - 1 ? WordCategory.values()[0] : WordCategory.values()[category.ordinal() + 1];
    }

    public static WordCategory getPreviousInOrder(WordCategory category) {
        return category.ordinal() == 0 ? WordCategory.values()[WordCategory.values().length - 1] : WordCategory.values()[category.ordinal() - 1];
    }

}
