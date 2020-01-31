package com.tom.controller;

import com.tom.model.Word;
import com.tom.model.WordCategory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoundScreenController {

    @FXML
    private Button close;
    @FXML
    private Pane round;
    @FXML
    private HBox wordRow;
    @FXML
    private Button go;
    @FXML
    private Label userInput;
    @FXML
    private StackPane gallows;
    @FXML
    private Label footerMessage;

    private MainController mainController;
    private Word wordToGuess;
    private Set<String> guessedLetters = new HashSet<>();
    private int chancesLeft;
    private WordCategory wordCategory;
    boolean winResult;

    public RoundScreenController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initialize() {
        footerMessage.setText(mainController.getFooterMessageText());
    }

    public void showScreen() {
        this.guessedLetters.clear();
        this.wordToGuess = getRandomWordToGuess(wordCategory);
        this.chancesLeft = 8;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/RoundScreen.fxml"));
        fxmlLoader.setController(this);
        Pane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainController.setMainPane(pane);

        showWordToGuess();
        userInput.setText("");
        go.requestFocus();
        showHangman();

        round.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.SPACE) {
                checkLetter();
            }
            userInputSetLetter(key.getCode().toString());
        });
    }

    public void setWordCategory(WordCategory wordCategory) {
        this.wordCategory = wordCategory;
    }

    private void showHangman() {
        int index = 8 - chancesLeft;
        String link = "/img/hangman" + index + ".png";

        ImageView imgV = new ImageView();
        Image img = new Image(link);
        imgV.setImage(img);

        gallows.getChildren().clear();
        gallows.getChildren().add(imgV);
    }

    private void showWordToGuess() {

        wordRow.getChildren().clear();
        for (int i = 0; i < wordToGuess.getWordLength(); i++) {
            Label letter = new Label();
            letter.setAlignment(Pos.CENTER);
            letter.setPrefHeight(67);
            letter.setPrefWidth(48);
            if (guessedLetters.contains(wordToGuess.getLetter(i))) {
                letter.setText(wordToGuess.getLetter(i));
                letter.getStyleClass().add("guessedLetter");
            } else {
                letter.setText("?");
                letter.getStyleClass().add("letterToGuess");
            }
            wordRow.getChildren().add(letter);
        }
    }

    @FXML
    private void checkLetter() {
        String letter = userInput.getText();
        if (letter.length() > 0) {
            if (!guessedLetters.contains(letter) && wordToGuess.contains(letter) > 0) {
                guessedLetters.add(letter);
                if (checkWin()) {
                    endGame(true);
                }
                showWordToGuess();
            } else if (!guessedLetters.contains(letter)) {
                guessedLetters.add(letter);
                deleteChance();
                showHangman();
            }
        }
    }

    private void deleteChance() {
        if (chancesLeft > 0) {
            chancesLeft -= 1;
        }
        if (chancesLeft == 0) {
            endGame(false);
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < wordToGuess.getWordLength(); i++) {
            if (!guessedLetters.contains(wordToGuess.getLetter(i))) {
                return false;
            }
        }
        return true;
    }

    @FXML
    private void endGame(boolean winResult) {
        mainController.endGame(winResult);
    }

    private void userInputSetLetter(String letter) {
        if (letter.matches("[A-Za-z*]")) {
            userInput.setText(letter);
        }
    }

    private Word getRandomWordToGuess(WordCategory wordCategory) {

        InputStream inputStream = this.getClass().getResourceAsStream(wordCategory.getDataFile());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        Stream<String> wynik = null;
        try {
            wynik = bufferedReader.lines();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> lines = wynik.collect(Collectors.toList());
        ArrayList<String> words = new ArrayList<>(lines);
        Collections.shuffle(words);

        wordToGuess = new Word(words.get(0).toUpperCase());
        System.out.println(wordToGuess.getWord());
        return wordToGuess;
    }

    @FXML
    public void exitGame() {
        mainController.exitGame();
    }
}
