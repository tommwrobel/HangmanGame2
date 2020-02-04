package com.tom.controller;

import com.tom.model.Sound;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class ScreenController {

    protected final MainController mainController;

    public ScreenController(MainController mainController) {
        this.mainController = mainController;
    }

    protected Pane loadPane(String location) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource(location));
        fxmlLoader.setController(this);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load pane: " + location, e);
        }
    }

    @FXML
    public void clickSound() {
        mainController.getSoundController().play(Sound.CLICK, false);
    }

    @FXML
    public void backToMainMenu() {
        mainController.startGame();
    }

    @FXML
    public void exitGame() {
        mainController.exitGame();
    }
}