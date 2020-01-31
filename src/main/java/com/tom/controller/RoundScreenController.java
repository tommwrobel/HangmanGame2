package com.tom.controller;

import com.tom.model.Word;
import com.tom.model.WordCategory;
import javafx.fxml.FXML;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class RoundScreenController extends ScreenController {
    private static final String ROUND_SCREEN = "/fxml/RoundScreen.fxml";

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
    private List<String> words;

    public RoundScreenController(MainController mainController) {
        this.mainController = mainController;
    }

    public void showScreen() {
        this.guessedLetters.clear();
        this.wordToGuess = getRandomWordToGuess(wordCategory);
        this.chancesLeft = 8;

        Pane pane = loadPane(ROUND_SCREEN);
        mainController.setMainPane(pane);

        showWordToGuess();
        userInput.setText("");
        go.requestFocus();
        showHangman();

        round.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyEvent);
    }

    public void setWordCategory(WordCategory wordCategory) {
        this.wordCategory = wordCategory;
    }

    private void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            checkLetter();
        }
        userInputSetLetter(keyEvent.getCode().toString());
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
        for (int i = 0; i < wordToGuess.getLength(); i++) {
            Label letter = new Label();
            letter.setAlignment(Pos.CENTER);
            letter.setPrefHeight(67);
            letter.setPrefWidth(48);
            if (guessedLetters.contains(wordToGuess.getLetterAt(i))) {
                letter.setText(wordToGuess.getLetterAt(i));
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
            if (!guessedLetters.contains(letter) && wordToGuess.containsLetter(letter) > 0) {
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
        } else if (chancesLeft == 0) {
            endGame(false);
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < wordToGuess.getLength(); i++) {
            if (!guessedLetters.contains(wordToGuess.getLetterAt(i))) {
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
        loadWords();
        wordToGuess = new Word(words.get(0).toUpperCase());
        return wordToGuess;
    }

    private void loadWords() {
        if (words != null) {
            return;
        }
        String pathToFile = this.getClass().getResource(wordCategory.getDataFile()).getFile();
        File file = new File(pathToFile);
        try {
            words = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Collections.shuffle(words);
    }

    @FXML
    public void exitGame() {
        mainController.exitGame();
    }
}
