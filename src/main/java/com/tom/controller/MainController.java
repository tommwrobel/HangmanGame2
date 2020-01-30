package com.tom.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController {



    @FXML
    private Pane mainPane;

    @FXML
    public void initialize() {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/MainMenu.fxml"));

        Pane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }

        MainMenuController mainMenuController = fxmlLoader.getController();
        mainMenuController.setMainController(this);

        setMainPane(pane);
    }

    public void setMainPane(Pane pane) {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    }

    public void exitGame() {
        Platform.exit();
    }
}
