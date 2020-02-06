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
    private RoundSettingsScreenController roundSettingsScreenController;
    private RoundScreenController roundScreenController;
    private EndGameScreenController endGameScreenController;
    private String footerMessageText;
    private SoundController soundController;

    public MainController() {
        this.soundController = new SoundController();
        this.footerMessageText = "HangmanGame2 v.1.1 © 2020 by Tomasz Wróbel.";
    }

    @FXML
    public void initialize() {
        showMainMenuScreen();
        soundController.play(Sound.MAIN_MUSIC, true);
    }

    public void setMainPane(Pane pane) {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    }

    public void showMainMenuScreen() {
        mainMenuScreenController = new MainMenuScreenController(this);
        mainMenuScreenController.showScreen();
    }

    public void showRoundSettingsScreen() {
        roundSettingsScreenController = new RoundSettingsScreenController(this);
        roundSettingsScreenController.showScreen();
    }

    public void showRoundScreen(DifficultyLevel difficultyLevel, Category category) {
        roundScreenController = new RoundScreenController(this, difficultyLevel, category);
        roundScreenController.showScreen();
    }

    public void showEndGameScreen(boolean winResult, Word wordToGuess, Set<String> guessedLetters) {
        endGameScreenController = new EndGameScreenController(this, winResult, wordToGuess, guessedLetters);
        endGameScreenController.showScreen();
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
