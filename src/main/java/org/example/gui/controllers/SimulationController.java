package org.example.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SimulationController extends VBox {
    @FXML
    private Label testLabel;

    public SimulationController(String message) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/simulation.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        testLabel.setText(message);
    }

    public void setText(String message){
        testLabel.setText(message);
    }
}
