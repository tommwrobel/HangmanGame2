package com.tom.controller;

import com.tom.model.Sound;
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

    public MainMenuScreenController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initialize() {
        footerMessage.setText(mainController.getFooterMessageText());
    }

    public void showScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/MainMenuScreen.fxml"));
        fxmlLoader.setController(this);
        Pane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainController.setMainPane(pane);
    }

    @FXML
    public void chooseCategory() {
        mainController.chooseCategory();
    }

    @FXML
    public void exitGame() {
        mainController.exitGame();
    }
}
