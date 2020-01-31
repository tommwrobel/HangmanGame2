package com.tom.controller;

import com.tom.model.Sound;
import com.tom.model.WordCategory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MainController {

    @FXML
    private Pane mainPane;

    private final MainMenuScreenController mainMenuScreenController;
    private final CategoryModalScreenController categoryModalScreenController;
    private final RoundScreenController roundScreenController;
    private final EndGameScreenController endGameScreenController;
    private final String footerMessageText;
    private final SoundController soundController;

    public MainController() {
        mainMenuScreenController = new MainMenuScreenController(this);
        categoryModalScreenController = new CategoryModalScreenController(this);
        roundScreenController = new RoundScreenController(this);
        endGameScreenController = new EndGameScreenController(this);
        soundController = new SoundController();
        footerMessageText = "HangmanGame2 v.1.0 © 2020 by Tomasz Wróbel.";
    }

    @FXML
    public void initialize() {
        mainMenuScreenController.showScreen();
        soundController.play(Sound.MAIN_MUSIC, true);

        mainPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (key) -> {
            soundController.play(Sound.CLICK, false);
        });
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

    public void startRound(WordCategory wordCategory) {
        roundScreenController.setWordCategory(wordCategory);
        roundScreenController.showScreen();
    }

    public void endGame(boolean winResult) {
        endGameScreenController.showScreen(winResult);
    }

    public void exitGame() {
        Platform.exit();
    }
}
