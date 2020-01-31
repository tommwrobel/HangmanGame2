package com.tom.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class EndGameScreenController {

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

    public void initialize() {
        footerMessage.setText(mainController.getFooterMessageText());
    }

    public void showScreen(boolean winResult) {

        Image imgSource;

        if(winResult == true) {
            imgSource = new Image("/img/winModal.png");
        } else {
            imgSource = new Image("/img/looseModal.png");
        }

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/EndGameScreen.fxml"));
        fxmlLoader.setController(this);
        Pane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
