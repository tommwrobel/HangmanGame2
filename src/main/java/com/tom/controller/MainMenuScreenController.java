package com.tom.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainMenuScreenController {

    @FXML
    private Button newGame;
    @FXML
    private Button close;
    @FXML
    private Label footerMessage;

    private MainController mainController;
    private FXMLLoader fxmlLoader;

    public MainMenuScreenController(MainController mainController) {
        this.mainController = mainController;
        this.fxmlLoader = new FXMLLoader();
        this.fxmlLoader.setLocation(this.getClass().getResource("/fxml/MainMenuScreen.fxml"));
        this.fxmlLoader.setController(this);
    }

    public void initialize() {
        footerMessage.setText(mainController.getFooterMessageText());
    }

    public void showScreen() {
        Pane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }
        mainController.setMainPane(pane);
    };

    @FXML
    public void startGame() {
        mainController.startGame();
    }

    @FXML
    public void exitGame() {
        mainController.exitGame();
    }
}
