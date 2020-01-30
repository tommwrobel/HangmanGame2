package com.tom.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainMenuController {

    private MainController mainController;

    @FXML
    private Button newGame;

    @FXML
    private Button close;

    @FXML
    private Label footerMessage;

    public void initialize() {}

    public void startGame() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/CategoryModal.fxml"));
        Pane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }

        CategoryModalController categoryModalController = fxmlLoader.getController();
        categoryModalController.setMainController(mainController);

        mainController.setMainPane(pane);
    }

    public void exitGame() {
        mainController.exitGame();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
