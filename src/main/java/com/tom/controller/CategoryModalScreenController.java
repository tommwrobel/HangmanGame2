package com.tom.controller;

import com.tom.model.WordCategory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CategoryModalScreenController extends ScreenController{
    private static final String CATEGORY_MODAL_SCREEN = "/fxml/CategoryModalScreen.fxml";

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

    private WordCategory currentWordCategory;

    public CategoryModalScreenController(MainController mainController) {
        super(mainController);
        currentWordCategory = WordCategory.values()[0];
    }

    public void showScreen() {
        Pane pane = loadPane(CATEGORY_MODAL_SCREEN);
        mainController.setMainPane(pane);
    }

    @FXML
    public void displayNextCategory() {
        currentWordCategory = WordCategory.getNextInOrder(currentWordCategory);
        category.setText(currentWordCategory.getDisplayName());
    }

    @FXML
    public void displayPrevCategory() {
        currentWordCategory = WordCategory.getPreviousInOrder(currentWordCategory);
        category.setText(currentWordCategory.getDisplayName());
    }

    @FXML
    public void startRound() {
        mainController.startRound(currentWordCategory);
    }
}
