package com.tom.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class EndGameScreenController extends ScreenController{
    private final String WIN_MODAL = "/img/winModal.png";
    private final String LOOSE_MODAL = "/img/looseModal.png";
    private final String END_GAME_SCREEN = "/fxml/EndGameScreen.fxml";

    @FXML
    private Button yes;
    @FXML
    private Button no;
    @FXML
    private Button prev;
    @FXML
    private ImageView gameResultImage;
    @FXML
    private Label footerMessage;

    private MainController mainController;

    public EndGameScreenController(MainController mainController) {
        this.mainController = mainController;
    }

    public void showScreen(boolean isWin) {
        String imgPath = isWin ? WIN_MODAL : LOOSE_MODAL;
        Image imgSource = new Image(imgPath);
        Pane pane = loadPane(END_GAME_SCREEN);
        gameResultImage.setImage(imgSource);
        mainController.setMainPane(pane);
    }

    @FXML
    public void startGame() {
        mainController.chooseCategory();
    }

    @FXML
    public void goToMainMenu() {
        mainController.startGame();
    }

    @FXML
    public void exitGame() {
        mainController.exitGame();
    }
}
