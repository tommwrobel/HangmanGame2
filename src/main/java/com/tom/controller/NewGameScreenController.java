package com.tom.controller;

import com.tom.model.Category;
import com.tom.model.DifficultyLevel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class NewGameScreenController extends ScreenController {

    @FXML
    private Pane newGameScreen;
    @FXML
    private HBox diffLvlButtons;
    @FXML
    private Button btnNextCategory;
    @FXML
    private Button btnPrevCategory;
    @FXML
    private Label activeCategory;
    @FXML
    private Label footerMessage;

    private static final String NEW_GAME_SCREEN = "/fxml/NewGameScreen.fxml";
    private DifficultyLevel currentDifficultyLevel;
    private Category currentCategory;

    public NewGameScreenController(MainController mainController) {
        super(mainController);
        currentDifficultyLevel = DifficultyLevel.values()[0];
        currentCategory = Category.values()[0];
    }

    public void initialize() {
        footerMessage.setText(mainController.getFooterMessageText());
        selectDifficultyLevel(DifficultyLevel.values()[0]);
        activeCategory.setText(currentCategory.getDisplayName());
    }

    public void showScreen() {
        currentDifficultyLevel = DifficultyLevel.values()[0];
        currentCategory = Category.values()[0];

        Pane pane = loadPane(NEW_GAME_SCREEN);
        mainController.setMainPane(pane);
    }

    @FXML
    public void selectDifficultyLevel(DifficultyLevel difficultyLevel) {

        for (DifficultyLevel diffLvl : DifficultyLevel.values()) {

            Button diffLvlButton = new Button(diffLvl.getDisplayName());
            diffLvlButton.setId("diff" + diffLvl.getDisplayName());
            diffLvlButton.getStyleClass().add("Button");
            diffLvlButton.setOnAction(event -> changeDifficultyLevel(event));
            if (diffLvl == difficultyLevel) {
                diffLvlButton.getStyleClass().add("btnDiffLvlActive");
            } else {
                diffLvlButton.getStyleClass().add("btnDiffLvl");
            }
            diffLvlButtons.getChildren().add(diffLvlButton);
        }
        currentDifficultyLevel = difficultyLevel;
    }

    @FXML
    public void changeDifficultyLevel(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        if(!clicked.getId().equals("diff" + currentDifficultyLevel.getDisplayName())) {
            currentDifficultyLevel = DifficultyLevel.valueOf(clicked.getText());

            for (DifficultyLevel diffLvl : DifficultyLevel.values()) {
                Node node = diffLvlButtons.lookup("#diff" + diffLvl.getDisplayName());
                node.getStyleClass().clear();
                node.getStyleClass().add("button");
                if(currentDifficultyLevel == diffLvl) {
                    node.getStyleClass().add("btnDiffLvlActive");
                } else {
                    node.getStyleClass().add("btnDiffLvl");
                }
            }
        }
    }

    @FXML
    public void displayNextCategory() {
        currentCategory = Category.getNextInOrder(currentCategory);
        activeCategory.setText(currentCategory.getDisplayName());
    }

    @FXML
    public void displayPrevCategory() {
        currentCategory = Category.getPrevInOrder(currentCategory);
        activeCategory.setText(currentCategory.getDisplayName());
    }

    @FXML
    public void startRound() {
        mainController.startRound(currentDifficultyLevel, currentCategory);
    }
}
