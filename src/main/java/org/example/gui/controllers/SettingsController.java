package org.example.gui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class SettingsController extends VBox {

    // Radio buttons for different variants
    @FXML private RadioButton globeRadio;
    @FXML private RadioButton hellPortalRadio;
    @FXML private RadioButton grassFieldRadio;
    @FXML private RadioButton toxicCorpseRadio;
    @FXML private RadioButton randomRadio;
    @FXML private RadioButton correctionRadio;
    @FXML private RadioButton predestinationRadio;
    @FXML private RadioButton madnessRadio;

    // TextFields for custom configuration
    @FXML private TextField mapWidthInput;
    @FXML private TextField mapHeightInput;
    @FXML private TextField initialPlantsInput;
    @FXML private TextField plantsEnergyInput;
    @FXML private TextField plantsGrowthInput;
    @FXML private TextField initialAnimalsInput;
    @FXML private TextField stuffedEnergyInput;
    @FXML private TextField initialEnergyInput;
    @FXML private TextField reproduceEnergyInput;
    @FXML private TextField minMutationsInput;
    @FXML private TextField maxMutationsInput;
    @FXML private TextField genomeLengthInput;


    @FXML private Button startButton;

    public SettingsController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void startSimulation(){
    }
}
