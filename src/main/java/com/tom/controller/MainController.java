package com.tom.controller;

import com.tom.model.WordCategory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MainController {

    private MainMenuScreenController mainMenuScreenController;
    private CategoryModalScreenController categoryModalScreenController;
    private RoundScreenController roundScreenController;
    private EndGameScreenController endGameScreenController;
    private String footerMessageText;

    @FXML
    private Pane mainPane;

    public MainController() {
        mainMenuScreenController = new MainMenuScreenController(this);
        categoryModalScreenController = new CategoryModalScreenController(this);
        roundScreenController = new RoundScreenController(this);
        endGameScreenController = new EndGameScreenController(this);
        footerMessageText = "HangmanGame2 v.1.0 © 2020 by Tomasz Wróbel.";
    }

    @FXML
    public void initialize() {
        mainMenuScreenController.showScreen();
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

    public String getFooterMessageText() {
        return footerMessageText;
    }

    public void exitGame() {
        Platform.exit();
    }
}
