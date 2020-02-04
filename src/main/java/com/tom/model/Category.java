package com.tom.model;

public enum Category {
    ANIMALS("/data/animals.txt"),
    COUNTRIES("/data/countries.txt"),
    PREFESSIONS("/data/professions.txt");

    private String dataFile;

    Category(String dataFile) {
        this.dataFile = dataFile;
    }

    public String getDataFile() {
        return dataFile;
    }

    public String getDisplayName() {
        return this.name();
    }

    public static Category getNextInOrder(Category category) {
        return category.ordinal() == values().length - 1 ? Category.values()[0] : Category.values()[category.ordinal() + 1];
    }

    public static Category getPrevInOrder(Category category) {
        return category.ordinal() == 0 ? Category.values()[Category.values().length - 1] : Category.values()[category.ordinal() - 1];
    }
}
