package com.tom.controller;

import com.tom.model.Sound;
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
        mainController.getSoundController().play(Sound.MAIN_MUSIC, true);
    }

    public void initialize() {
        footerMessage.setText(mainController.getFooterMessageText());
    }

    public void showScreen() {
        Pane pane = loadPane(MAIN_MENU_SCREEN);
        mainController.setMainPane(pane);
    }

    @FXML
    public void showRoundSettingsScreen() {
        mainController.showRoundSettingsScreen();
    }
}
