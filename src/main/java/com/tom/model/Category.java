package com.tom.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Category {
    ANIMALS("/data/animals.txt"),
    COUNTRIES("/data/countries.txt"),
    PROFESSIONS("/data/professions.txt");

    private String dataFile;

    Category(String dataFile) {
        this.dataFile = dataFile;
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

    public String getRandomWord(int minLength, int maxLength) {
        InputStream inputStream = this.getClass().getResourceAsStream(dataFile);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        Stream<String> wynik = null;
        try {
            wynik = bufferedReader.lines();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> lines = wynik.filter(e -> (e.length() >= minLength) && e.length() <= maxLength).collect(Collectors.toList());
        ArrayList<String> words = new ArrayList<>(lines);
        Collections.shuffle(words);

        return words.get(0).toUpperCase();
    }
}
