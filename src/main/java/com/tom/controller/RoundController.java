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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoundController {

    @FXML
    private Button close;
    @FXML
    private Pane round;
    @FXML
    private HBox wordRow;
    @FXML
    private Button go;
    @FXML
    private Button yes;
    @FXML
    private Button no;
    @FXML
    private Label userInput;
    @FXML
    private Label roundInfo;
    @FXML
    private StackPane gallows;

    private MainController mainController;
    private Word wordToGuess;
    private Set<String> guessedLetters = new HashSet<>();
    private int chancesLeft;

    public RoundController(WordCategory wordCategory) {
        this.wordToGuess = getRandomWordToGuess(wordCategory);
        this.chancesLeft = 8;
    }

    public void initialize() {
        showWordToGuess();
        userInput.setText("");
        go.requestFocus();
        showHangman();

        round.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode() == KeyCode.SPACE) {
                checkLetter();
            }
            userInputSetLetter(key.getCode().toString());
        });
    }

    private void showHangman() {
        int index = 8-chancesLeft;
        String link = "/img/hangman" + index + ".png";

        ImageView imgV= new ImageView();
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
            if(guessedLetters.contains(wordToGuess.getLetter(i))) {
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

        if(letter.length() > 0) {
            if(!guessedLetters.contains(letter) && wordToGuess.contains(letter) > 0) {
                guessedLetters.add(letter);
                showWordToGuess();
            } else {
                deleteChance();
                showHangman();
            }
        }
    }

    private void deleteChance() {
        if(chancesLeft > 0) {
            chancesLeft -= 1;
        }
        if(chancesLeft == 0) {
            endGame();
        }
    }

    private void endGame() {
        String path = "/img/endgame.mp4";
        String pathToFile = this.getClass().getResource(path).getFile();
        Media media = new Media(new File(pathToFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        MediaView mediaView = new MediaView(mediaPlayer);

        round.getChildren().clear();
        round.getChildren().add(mediaView);
    }

    private void userInputSetLetter(String letter) {
        if(letter.matches("[A-Za-z*]")) {
            userInput.setText(letter);
        }
    }

    private Word getRandomWordToGuess(WordCategory wordCategory) {

        String pathToFile = this.getClass().getResource(wordCategory.getDataFile()).getFile();

        File file = new File(pathToFile);
        List<String> words = null;
        try {
            words = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(words);

        wordToGuess = new Word(words.get(0).toUpperCase());

        System.out.println(words.get(0));
        return wordToGuess;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void exitGame() {
        mainController.exitGame();
    }
}
