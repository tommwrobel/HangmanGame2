package com.tom.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WordTest {

    @Test
    @DisplayName("Method getLetterIndexes() should return list of proper letter indexes in given word")
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

    @ParameterizedTest(name = "#{index} Amount of ''{0}'' letter in word ''{1}'' is {2}")
    @CsvSource({
            "a, Abracadabra, 5",
            "e, Bee, 2",
            "T, Tetris, 2",
            "p, pPpPxP, 5"
    })
    @DisplayName("Method contains() should return proper number of given letter in given word")
    public void gettingNumbersOfLettersInWord(String givenLetter, String givenWord, int expectedAmount) {

        Word word;
        int actualAmout;

        word = new Word(givenWord);
        actualAmout = word.contains(givenLetter);

        assertThat(actualAmout).isEqualTo(expectedAmount);
    }

    @ParameterizedTest(name = "#{index} Length of ''{0}'' is {1}")
    @CsvSource({
            "Abracadabra, 11",
            "Bee, 3",
            "a, 1"
    })
    @DisplayName("Method .getWordLength() should return proper length of given word")
    public void methodShouldReturnProberWordLengthValue(String givenWord, int expectedLength) {

        Word wordClass;
        int result;

        wordClass = new Word(givenWord);
        result = wordClass.getWordLength();

        assertThat(result).isEqualTo(expectedLength);
    }

}