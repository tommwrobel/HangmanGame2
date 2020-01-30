package com.tom.controller;

import com.tom.model.WordCategory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CategoryModalController {

    private MainController mainController;
    private int wordCategoryIndex;

    @FXML
    private Button start;

    @FXML
    private Button next;

    @FXML
    private Button prev;

    @FXML
    private Label category;

    @FXML
    private Label footerMessage;

    public void CategoryModalController() {
        wordCategoryIndex = 0;
    }

    public void initialize() {
    }

    @FXML
    public void displayNextCategory() {
        int numberOfCategories = WordCategory.values().length;
        if (wordCategoryIndex < numberOfCategories - 1) {
            wordCategoryIndex += 1;
        } else {
            wordCategoryIndex = 0;
        }
        category.setText(WordCategory.values()[wordCategoryIndex].getDisplayName());
    }

    @FXML
    public void displayPrevCategory() {
        int numberOfCategories = WordCategory.values().length;
        if (wordCategoryIndex > 0) {
            wordCategoryIndex -= 1;
        } else {
            wordCategoryIndex = numberOfCategories-1;
        }
        category.setText(WordCategory.values()[wordCategoryIndex].getDisplayName());
    }

    @FXML
    public void startRound() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/Round.fxml"));
        RoundController roundController = new RoundController(WordCategory.values()[wordCategoryIndex]);
        fxmlLoader.setController(roundController);
        Pane pane = null;

        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        roundController.setMainController(mainController);
        mainController.setMainPane(pane);
    }

    public void exitGame() {
        mainController.exitGame();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
