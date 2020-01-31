package com.tom.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MainMenuScreenController extends ScreenController{
    private static final String MAIN_MENU_SCREEN = "/fxml/MainMenuScreen.fxml";

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

    public void showScreen() {
        Pane pane = loadPane(MAIN_MENU_SCREEN);
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
