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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoundScreenController extends ScreenController {

    @FXML
    private Pane roundScreen;
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
    private Label footerMessage;
    @FXML
    private ImageView sun;
    @FXML
    private ImageView backgroundSky;
    @FXML
    private ImageView backgroundSkyRed;
    @FXML
    private ImageView backgroundFirst;

    private static final String ROUND_SCREEN = "/fxml/RoundScreen.fxml";

    private DifficultyLevel difficultyLevel;
    private Category category;
    private Word wordToGuess;
    private int timer;
    private Timeline timeline;
    private Set<String> guessedLetters = new HashSet<>();
    private int chancesLeft;

    public RoundScreenController(MainController mainController) {
        super(mainController);
        this.timer = 0;
    }

    public void initialize() {
        footerMessage.setText(mainController.getFooterMessageText());
        timeLeft.setText("00:00");
    }

    public void setupNewRound(DifficultyLevel difficultyLevel, Category category) {
        this.difficultyLevel = difficultyLevel;
        this.category = category;
        this.guessedLetters.clear();
        this.wordToGuess = getRandomWordToGuess(category);
        this.chancesLeft = difficultyLevel.getChances();
        this.timer = 0;
    }

    public void showScreen() {
        Pane pane = loadPane(ROUND_SCREEN);
        mainController.setMainPane(pane);

        showWordToGuess();
        showStatement(RoundStatement.HELLO);
        userInput.setText("");
        btnGo.requestFocus();
        buildHangman(10, 11 - difficultyLevel.getChances());
        sunDown();

        roundScreen.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.SPACE || key.getCode() == KeyCode.ENTER) {
                checkLetter();
            }
            userInputSetLetter(key.getCode().toString());
        });

        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            int allSecondsLeft = difficultyLevel.getRoundTime() - timer;
            if (allSecondsLeft >= 0) {
                int secondsLeft = allSecondsLeft % 60;
                int minutesLeft = allSecondsLeft / 60;
                timeLeft.setText(String.format("%02d:%02d", minutesLeft, secondsLeft));
                timer++;
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
                endGame(false);
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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

    private void buildHangman(int from, int numberOfPieces) {
        for (int i = 0; i < numberOfPieces; i++) {
            String link = "/img/hm" + (from - i) + ".png";
            Image img = new Image(link);
            ImageView imgV = new ImageView(img);
            gallows.getChildren().add(imgV);

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2), imgV);
            translateTransition.setFromY(-200);
            translateTransition.setToY(0);
            translateTransition.setInterpolator(Interpolator.EASE_IN);
            translateTransition.play();
        }
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
                    endGame(true);
                }
            } else if (!guessedLetters.contains(letter)) {
                guessedLetters.add(letter);
                deleteChance();
                buildHangman(chancesLeft, 1);
                showStatement(RoundStatement.BAD_ANSWER);
            } else {
                showStatement(RoundStatement.DUBEL);
            }
        }
    }

    private void deleteChance() {
        if (chancesLeft == 1) {
            endGame(false);
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
    private void endGame(boolean winResult) {
        mainController.endGame(winResult, wordToGuess, guessedLetters);
    }

    private void userInputSetLetter(String letter) {
        if (letter.matches("[A-Za-z*]")) {
            letter = letter.toUpperCase();
            userInput.setText(letter);
        }
    }

    private Word getRandomWordToGuess(Category category) {

        InputStream inputStream = this.getClass().getResourceAsStream(category.getDataFile());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        Stream<String> wynik = null;
        try {
            wynik = bufferedReader.lines();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> lines = wynik.collect(Collectors.toList());
        ArrayList<String> words = new ArrayList<>(lines);
        Collections.shuffle(words);

        wordToGuess = new Word(words.get(0).toUpperCase());
        System.out.println(wordToGuess.getWord());
        return wordToGuess;
    }
}
