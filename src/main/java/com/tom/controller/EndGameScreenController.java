package com.tom.controller;

import com.tom.model.Word;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class EndGameScreenController extends ScreenController {

    @FXML
    private ImageView gameResultTitle;
    @FXML
    private Label footerMessage;
    @FXML
    private HBox wordRow;
    @FXML
    private StackPane gallows;

    private final String GAME_TITLE_WIN = "/img/gameTitleWin.png";
    private final String GAME_TITLE_LOOSE = "/img/gameTitleLoose.png";
    private final String HANGMAN_URL_WIN = "/img/hmWin.png";
    private final String HANGMAN_URL_LOOSE = "/img/hmLoose.png";


    public static final String END_GAME_SCREEN = "/fxml/EndGameScreen.fxml";

    public EndGameScreenController(MainController mainController) {
        super(mainController);
    }

    public void initialize() {
        footerMessage.setText(mainController.getFooterMessageText());
    }

    public void showScreen(boolean winResult, Word wordToGuess, Set<String> guessedLetters) {
        Pane pane = loadPane(END_GAME_SCREEN);
        mainController.setMainPane(pane);

        Image gameResultTitleImage = new Image(winResult ? GAME_TITLE_WIN : GAME_TITLE_LOOSE);
        gameResultTitle.setImage(gameResultTitleImage);
        showHangman(winResult ? HANGMAN_URL_WIN : HANGMAN_URL_LOOSE);

        showFinalWord(wordToGuess, guessedLetters);
    }

    private void showHangman(String imageUrl) {
        Image img = new Image(imageUrl);
        ImageView imgV = new ImageView(img);
        gallows.getChildren().add(imgV);
    }


    private void showFinalWord(Word wordToGuess, Set<String> guessedLetters) {
        wordRow.getChildren().clear();
        for (int i = 0; i < wordToGuess.getWordLength(); i++) {

            StackPane letterStackPane = new StackPane();
            letterStackPane.setAlignment(Pos.CENTER);
            letterStackPane.setPrefHeight(65);
            letterStackPane.setPrefWidth(48);
            letterStackPane.getStyleClass().add("letterBackgroundPane");

            Label letter = new Label();
            letter.setAlignment(Pos.CENTER);
            letter.setText(wordToGuess.getLetter(i));

            letterStackPane.getChildren().add(letter);
            if (guessedLetters.contains(wordToGuess.getLetter(i))) {
                letter.getStyleClass().add("guessedLetterEndCorrect");
            } else {
                letter.getStyleClass().add("guessedLetterEndWrong");
            }

            wordRow.getChildren().add(letterStackPane);
        }
    }

    @FXML
    public void startGame() {
        mainController.chooseCategory();
    }

    @FXML
    public void goToMainMenu() {
        mainController.startGame();
    }
}
