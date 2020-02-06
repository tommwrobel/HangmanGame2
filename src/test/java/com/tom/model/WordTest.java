package com.tom.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WordTest {

    @Test
    public void gettingLetterIndexesListShouldReturnProperIndexes() {
        String wordToTest;
        String letterToCheck;
        List<Integer> finalValues;
        Word word;

        wordToTest = "Abracadabra";
        letterToCheck = "a";
        word = new Word(wordToTest);
        finalValues = new ArrayList<>(word.getLetterIndexes(letterToCheck));

        assertThat(finalValues).contains(0, 3, 5, 7, 10);
    }

    @Test
    public void gettingNumbersOfLettersInWord() {
        String wordToTest;
        String letterToCheck;
        Word word;
        int result;

        wordToTest = "Abracadabra";
        letterToCheck = "a";
        word = new Word(wordToTest);
        result = word.contains(letterToCheck);

        assertThat(result).isEqualTo(5);
    }

    @Test
    public void methodShouldReturnProberWordLengthValue() {

        String wordToTest;
        Word word;
        int result;

        wordToTest = "Abracadabra";
        word = new Word(wordToTest);
        result = word.getWordLength();

        assertThat(result).isEqualTo(11);
    }

}