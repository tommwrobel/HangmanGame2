package com.tom.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MainMenuScreenController extends ScreenController {

    @FXML
    private Button btnNewGame;
    @FXML
    private Button btnClose;
    @FXML
    private Label footerMessage;

    private static final String MAIN_MENU_SCREEN = "/fxml/MainMenuScreen.fxml";

    public MainMenuScreenController(MainController mainController) {
        super(mainController);
    }

    public void initialize() {
        footerMessage.setText(mainController.getFooterMessageText());
    }

    public void showScreen() {
        Pane pane = loadPane(MAIN_MENU_SCREEN);
        mainController.setMainPane(pane);
    }

    @FXML
    public void chooseCategory() {
        mainController.chooseCategory();
    }
}
