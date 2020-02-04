package com.tom.controller;

import com.tom.model.Category;
import com.tom.model.DifficultyLevel;
import com.tom.model.Sound;
import com.tom.model.Word;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.Set;

public class MainController {

    @FXML
    private Pane mainPane;

    private MainMenuScreenController mainMenuScreenController;
    private NewGameScreenController categoryModalScreenController;
    private RoundScreenController roundScreenController;
    private EndGameScreenController endGameScreenController;
    private String footerMessageText;
    private SoundController soundController;

    public MainController() {
        mainMenuScreenController = new MainMenuScreenController(this);
        categoryModalScreenController = new NewGameScreenController(this);
        roundScreenController = new RoundScreenController(this);
        endGameScreenController = new EndGameScreenController(this);
        soundController = new SoundController();
        footerMessageText = "HangmanGame2 v.1.0 © 2020 by Tomasz Wróbel.";
    }

    @FXML
    public void initialize() {
        mainMenuScreenController.showScreen();
        soundController.play(Sound.MAIN_MUSIC, true);
    }

    public void setMainPane(Pane pane) {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    }

    public void startGame() {
        mainMenuScreenController.showScreen();
    }

    public void chooseCategory() {
        categoryModalScreenController.showScreen();
    }

    public void startRound(DifficultyLevel difficultyLevel, Category category) {
        roundScreenController.setupNewRound(difficultyLevel, category);
        roundScreenController.showScreen();
    }

    public void endGame(boolean winResult, Word wordToGuess, Set<String> guessedLetters) {
        endGameScreenController.showScreen(winResult, wordToGuess, guessedLetters);
    }

    public SoundController getSoundController() {
        return soundController;
    }

    public String getFooterMessageText() {
        return footerMessageText;
    }

    public void exitGame() {
        Platform.exit();
    }
}
