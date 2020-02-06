package com.tom.controller;

import com.tom.model.Category;
import com.tom.model.DifficultyLevel;
import com.tom.model.RoundStatement;
import com.tom.model.Word;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoundScreenController extends ScreenController {

    @FXML
    private Pane roundScreenMainPane;
    @FXML
    private Button btnClose;
    @FXML
    private HBox wordRow;
    @FXML
    private Button btnGo;
    @FXML
    private Label userInput;
    @FXML
    private Label timeLeft;
    @FXML
    private Label roundStatementText;
    @FXML
    private StackPane gallows;
    @FXML
    private ImageView sun;
    @FXML
    private ImageView backgroundSky;
    @FXML
    private ImageView backgroundSkyRed;
    @FXML
    private ImageView backgroundFirst;
    @FXML
    private Label footerMessage;

    private static final String ROUND_SCREEN = "/fxml/RoundScreen.fxml";

    private DifficultyLevel difficultyLevel;
    private Word wordToGuess;
    private int chancesLeft;
    private int secondsPassed;
    private Timeline timeline;
    private Set<String> guessedLetters = new HashSet<>();

    public RoundScreenController(MainController mainController, DifficultyLevel difficultyLevel, Category category) {
        super(mainController);

        this.difficultyLevel = difficultyLevel;
        this.wordToGuess = new Word(category.getRandomWord(difficultyLevel.getMinWordLength(), difficultyLevel.getMaxWordLength()));
        System.out.println(wordToGuess.getWord());
        this.secondsPassed = 0;
        this.guessedLetters.clear();
        this.chancesLeft = 10;
    }

    public void initialize() {
        footerMessage.setText(mainController.getFooterMessageText());
        timeLeft.setText(formatTime(difficultyLevel.getRoundTime()));
    }

    public void showScreen() {
        Pane pane = loadPane(ROUND_SCREEN);
        mainController.setMainPane(pane);

        showWordToGuess();
        showStatement(RoundStatement.HELLO);
        userInput.setText("");
        btnGo.requestFocus();
        sunDown();

        roundScreenMainPane.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.SPACE || key.getCode() == KeyCode.ENTER) {
                checkLetter();
            } else if (key.getCode().isLetterKey()) {
                userInput.setText(key.getCode().toString().toUpperCase());
            } else {
                showStatement(RoundStatement.WRONG_KEY);
            }
        });

        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            int allSecondsLeft = difficultyLevel.getRoundTime() - secondsPassed - 1;
            if (allSecondsLeft >= 0) {
                timeLeft.setText(formatTime(allSecondsLeft));
                secondsPassed++;
                if (allSecondsLeft < 6) {
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), timeLeft);
                    scaleTransition.setFromX(1.1);
                    scaleTransition.setFromY(1.1);
                    scaleTransition.setToX(1.0);
                    scaleTransition.setToY(1.0);
                    scaleTransition.play();
                }
            }
            if (allSecondsLeft == 5) {
                showStatement(RoundStatement.TIME_OUT);
            }
            if (allSecondsLeft < 0) {
                timeline.stop();
                showEndGameScreen(false);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private String formatTime(int seconds) {
        int secondsLeft = seconds % 60;
        int minutesLeft = seconds / 60;
        return String.format("%02d:%02d", minutesLeft, secondsLeft);
    }

    private void sunDown() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(difficultyLevel.getRoundTime() + 1), sun);
        translateTransition.setFromY(0);
        translateTransition.setToY(180);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.play();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(difficultyLevel.getRoundTime() + 1), backgroundSkyRed);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    private void buildHangman() {
        String imgUrl = "/img/hm" + chancesLeft + ".png";
        Image img = new Image(imgUrl);
        ImageView imgV = new ImageView(img);
        gallows.getChildren().add(imgV);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2), imgV);
        translateTransition.setFromY(-200);
        translateTransition.setToY(0);
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        translateTransition.play();
    }

    private void showWordToGuess() {
        wordRow.getChildren().clear();
        for (int i = 0; i < wordToGuess.getWordLength(); i++) {

            StackPane letterStackPane = new StackPane();
            letterStackPane.setAlignment(Pos.CENTER);
            letterStackPane.setPrefHeight(65);
            letterStackPane.setPrefWidth(48);
            letterStackPane.getStyleClass().add("letterBackgroundPane");

            Label letter = new Label();
            letter.setAlignment(Pos.CENTER);
            letter.setId("letterNumber" + i);
            letter.setText("?");

            letterStackPane.getChildren().add(letter);

            letter.getStyleClass().add("letterToGuess");
            wordRow.getChildren().add(letterStackPane);
        }
    }

    private void updateLetterText(String letter) {
        List<Integer> letterIndexes;
        letterIndexes = wordToGuess.getLetterIndexes(letter);
        for (int letterIndex : letterIndexes) {
            Label labelToUpdate = (Label) wordRow.lookup("#letterNumber" + letterIndex);
            labelToUpdate.getStyleClass().clear();
            labelToUpdate.getStyleClass().add("guessedLetter");
            labelToUpdate.setText(letter);

            double fromX = 0;
            double fromY = 310;

            Bounds bounds = labelToUpdate.localToScene(labelToUpdate.getBoundsInLocal());
            fromX = 250 - (bounds.getMinX() + bounds.getMaxX()) / 2;

            TranslateTransition translateTransision = new TranslateTransition(Duration.seconds(0.3), labelToUpdate);
            translateTransision.setFromX(fromX);
            translateTransision.setFromY(fromY);
            translateTransision.setToX(0);
            translateTransision.setToY(0);
            translateTransision.setInterpolator(Interpolator.EASE_BOTH);
            translateTransision.play();
        }
    }

    private void showStatement(RoundStatement roundStatement) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), roundStatementText);
        scaleTransition.setFromX(1.3);
        scaleTransition.setFromY(1.3);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
        this.roundStatementText.setText(roundStatement.getStatement());
    }

    @FXML
    private void checkLetter() {
        String letter = userInput.getText();
        if (letter.length() > 0) {
            if (!guessedLetters.contains(letter) && wordToGuess.contains(letter) > 0) {
                guessedLetters.add(letter);
                updateLetterText(letter);
                showStatement(RoundStatement.GOOD_ANSWER);
                if (checkWin()) {
                    showEndGameScreen(true);
                }
            } else if (!guessedLetters.contains(letter)) {
                guessedLetters.add(letter);
                deleteChance();
                buildHangman();
                showStatement(RoundStatement.BAD_ANSWER);
            } else {
                showStatement(RoundStatement.DUBEL);
            }
        }
    }

    private void deleteChance() {
        if (chancesLeft == 1) {
            showEndGameScreen(false);
        } else {
            chancesLeft -= 1;
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < wordToGuess.getWordLength(); i++) {
            if (!guessedLetters.contains(wordToGuess.getLetter(i))) {
                return false;
            }
        }
        return true;
    }

    @FXML
    private void showEndGameScreen(boolean winResult) {
        timeline.stop();
        mainController.showEndGameScreen(winResult, wordToGuess, guessedLetters);
    }

    @Override
    public void backToMainMenu() {
        timeline.stop();
        super.backToMainMenu();
    }
}
