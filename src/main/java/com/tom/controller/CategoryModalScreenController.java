package com.tom.controller;

import com.tom.model.WordCategory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CategoryModalScreenController {

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

    private MainController mainController;
    private int wordCategoryIndex;
    private FXMLLoader fxmlLoader;

    public CategoryModalScreenController(MainController mainController) {
        this.mainController = mainController;
        this.wordCategoryIndex = 0;
        this.fxmlLoader = new FXMLLoader();
        this.fxmlLoader.setLocation(this.getClass().getResource("/fxml/CategoryModalScreen.fxml"));
        this.fxmlLoader.setController(this);
    }

    public void initialize() {
        footerMessage.setText(mainController.getFooterMessageText());
    }

    public void showScreen() {
        Pane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainController.setMainPane(pane);
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
            wordCategoryIndex = numberOfCategories - 1;
        }
        category.setText(WordCategory.values()[wordCategoryIndex].getDisplayName());
    }

    @FXML
    public void startRound() {
        mainController.startRound(WordCategory.values()[wordCategoryIndex]);
    }

    @FXML
    public void exitGame() {
        mainController.exitGame();
    }
}
