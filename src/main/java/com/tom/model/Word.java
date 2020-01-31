package com.tom.model;

import java.util.Arrays;
import java.util.List;

public class Word {

    private final String word;
    private final List<String> letters;

    public Word(String word) {
        this.word = word;
        this.letters = Arrays.asList(word.split("(?!^)"));
    }

    public int getLength() {
        return word.length();
    }

    public String getLetterAt(int index) {
        return letters.get(index).toUpperCase();
    }

    public int containsLetter(String letter) {
        int counter = 0;
        for (String letterInWord : letters) {
            if (letterInWord.equals(letter)) {
                counter++;
            }
        }
        return counter;
    }
}
