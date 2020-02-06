package com.tom.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Word {

    private String word;
    private List<String> letters;

    public Word(String word) {
        this.word = word.toUpperCase();
        this.letters = Arrays.asList(this.word.split("(?!^)"));
    }

    public String getWord() {
        return word;
    }

    public int getWordLength() {
        return word.length();
    }

    public String getLetter(int index) {
        return letters.get(index);
    }

    public List<Integer> getLetterIndexes(String letterToCheck) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < getWordLength(); i++) {
            if (letterToCheck.equalsIgnoreCase(getLetter(i))) {
                result.add(i);
            }
        }
        return result;
    }

    public int contains(String letterToCheck) {
        int counter = 0;
        for (String letterInWord : letters) {
            if (letterInWord.equalsIgnoreCase(letterToCheck)) {
                counter++;
            }
        }
        return counter;
    }
}
