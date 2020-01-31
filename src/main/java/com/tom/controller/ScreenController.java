package com.tom.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class ScreenController {

    protected Pane loadPane(String location) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource(location));
        fxmlLoader.setController(this);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load pane: " + location, e);
        }
    }

}
