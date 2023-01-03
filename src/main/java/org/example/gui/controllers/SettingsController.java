package org.example.gui.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.example.settings.InvalidSettingsException;
import org.example.settings.SimulationSettings;
import org.example.settings.variants.BehaviourVariant;
import org.example.settings.variants.MapVariant;
import org.example.settings.variants.MutationVariant;
import org.example.settings.variants.PlantsGrowthVariant;
import org.example.simulation.Simulation;

public class SettingsController extends VBox {

    // Radio buttons for different variants
    @FXML
    private RadioButton globeRadio;
    @FXML
    private RadioButton hellPortalRadio;
    @FXML
    private RadioButton grassFieldRadio;
    @FXML
    private RadioButton toxicCorpseRadio;
    @FXML
    private RadioButton randomRadio;
    @FXML
    private RadioButton correctionRadio;
    @FXML
    private RadioButton predestinationRadio;
    @FXML
    private RadioButton crazinessRadio;

    // TextFields for custom configuration
    @FXML
    private TextField mapWidthInput;
    @FXML
    private TextField mapHeightInput;
    @FXML
    private TextField initialPlantsInput;
    @FXML
    private TextField plantsEnergyInput;
    @FXML
    private TextField plantsGrowthInput;
    @FXML
    private TextField initialAnimalsInput;
    @FXML
    private TextField stuffedEnergyInput;
    @FXML
    private TextField initialEnergyInput;
    @FXML
    private TextField moveEnergyInput;
    @FXML
    private TextField reproduceEnergyInput;
    @FXML
    private TextField minMutationsInput;
    @FXML
    private TextField maxMutationsInput;
    @FXML
    private TextField genomeLengthInput;


    @FXML
    private Button startButton;
    @FXML
    private Button startFromFileButton;

    @FXML CheckBox csvCheckBox;

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

    private SimulationSettings prepareSettings() throws InvalidSettingsException {
        SimulationSettings settings = new SimulationSettings();

        // Get map variant
        if (globeRadio.isSelected()) {
            settings.setMapVariant(MapVariant.GLOBE);
        } else if (hellPortalRadio.isSelected()) {
            settings.setMapVariant(MapVariant.HELLISH_PORTAL);
        } else {
            throw new InvalidSettingsException("Map variant is required");
        }

        // Get plants growth variant
        if (grassFieldRadio.isSelected()) {
            settings.setPlantsGrowthVariant(PlantsGrowthVariant.GRASSY_EQUATORS);
        } else if (toxicCorpseRadio.isSelected()) {
            settings.setPlantsGrowthVariant(PlantsGrowthVariant.TOXIC_CORPSES);
        } else {
            throw new InvalidSettingsException("Plants growth variant is required");
        }

        // Get mutation variant
        if (randomRadio.isSelected()) {
            settings.setMutationVariant(MutationVariant.RANDOM);
        } else if (correctionRadio.isSelected()) {
            settings.setMutationVariant(MutationVariant.CORRECTION);
        } else {
            throw new InvalidSettingsException("Mutation variant is required");
        }

        // Get behaviour variant
        if (predestinationRadio.isSelected()) {
            settings.setBehaviourVariant(BehaviourVariant.PREDESTINATION);
        } else if (crazinessRadio.isSelected()) {
            settings.setBehaviourVariant(BehaviourVariant.CRAZY);
        } else {
            throw new InvalidSettingsException("Behaviour variant is required");
        }

        // Get rest of properties

        try {
            // Map properties
            settings.setMapWidth(Integer.parseInt(mapWidthInput.getText()));
            settings.setMapHeight(Integer.parseInt(mapHeightInput.getText()));

            // Plants properties
            settings.setInitialPlants(Integer.parseInt(initialPlantsInput.getText()));
            settings.setPlantsEnergy(Integer.parseInt(plantsEnergyInput.getText()));
            settings.setPlantsGrowth(Integer.parseInt(plantsGrowthInput.getText()));

            // Animal properties
            settings.setInitialAnimals(Integer.parseInt(initialAnimalsInput.getText()));
            settings.setInitialEnergy(Integer.parseInt(initialEnergyInput.getText()));
            settings.setMoveEnergy(Integer.parseInt(moveEnergyInput.getText()));
            settings.setStuffedEnergy(Integer.parseInt(stuffedEnergyInput.getText()));
            settings.setReproduceEnergy(Integer.parseInt(reproduceEnergyInput.getText()));
            settings.setMinMutations(Integer.parseInt(minMutationsInput.getText()));
            settings.setMaxMutations(Integer.parseInt(maxMutationsInput.getText()));
            settings.setGenomeLength(Integer.parseInt(genomeLengthInput.getText()));
        } catch (NumberFormatException e) {
            throw new InvalidSettingsException("One of provided values is not integer");
        }

        settings.setSaveToCSV(csvCheckBox.isSelected());

        return settings;
    }

    private void validateSettings(SimulationSettings settings) throws InvalidSettingsException {
        MapVariant mapVariant = settings.getMapVariant();
        if (!(mapVariant == MapVariant.GLOBE || mapVariant == MapVariant.HELLISH_PORTAL)) {
            throw new InvalidSettingsException("Illegal map variant");
        }

        PlantsGrowthVariant plantsGrowthVariant = settings.getPlantsGrowthVariant();
        if (!(plantsGrowthVariant == PlantsGrowthVariant.GRASSY_EQUATORS || plantsGrowthVariant == PlantsGrowthVariant.TOXIC_CORPSES)) {
            throw new InvalidSettingsException("Illegal plants growth variant");
        }

        MutationVariant mutationVariant = settings.getMutationVariant();
        if (!(mutationVariant == MutationVariant.CORRECTION || mutationVariant == MutationVariant.RANDOM)) {
            throw new InvalidSettingsException("Illegal mutation variant");
        }

        BehaviourVariant behaviourVariant = settings.getBehaviourVariant();
        if (!(behaviourVariant == BehaviourVariant.CRAZY || behaviourVariant == BehaviourVariant.PREDESTINATION)) {
            throw new InvalidSettingsException("Illegal behaviour variant");
        }

        if (settings.getMapWidth() <= 0 || settings.getMapHeight() <= 0) {
            throw new InvalidSettingsException("Map dimensions cannot be non-positive");
        }

        if (settings.getInitialPlants() < 0) {
            throw new InvalidSettingsException("Initial plants amount cannot be negative");
        }

        if (settings.getPlantsEnergy() <= 0) {
            throw new InvalidSettingsException("Energy from plants cannot be non-positive");
        }

        if (settings.getPlantsGrowth() < 0) {
            throw new InvalidSettingsException("Plants growth cannot be negative");
        }

        if (settings.getInitialAnimals() <= 0) {
            throw new InvalidSettingsException("Initial animals amount cannot be non-positive");
        }

        if (settings.getInitialEnergy() <= 0) {
            throw new InvalidSettingsException("Initial energy cannot be non-positive");
        }

        if (settings.getMoveEnergy() <= 0) {
            throw new InvalidSettingsException("Move energy cannot be non-positive");
        } else if(settings.getMoveEnergy() > settings.getInitialEnergy()){
            throw new InvalidSettingsException("Move energy cannot be greater than initial energy");
        }

        if (settings.getStuffedEnergy() <= 0) {
            throw new InvalidSettingsException("Stuffed energy cannot be non-positive");
        }

        if (settings.getReproduceEnergy() <= 0) {
            throw new InvalidSettingsException("Reproduce energy cannot be non-positive");
        }

        if (settings.getMinMutations() < 0) {
            throw new InvalidSettingsException("Minimum mutations amount cannot be negative");
        }

        if (settings.getMaxMutations() < 0) {
            throw new InvalidSettingsException("Maximum mutations amount cannot be negative");
        } else if (settings.getMaxMutations() < settings.getMinMutations()) {
            throw new InvalidSettingsException("Maximum mutations amount cannot be less than minimum mutations amount");
        }

        if (settings.getGenomeLength() <= 0) {
            throw new InvalidSettingsException("Genome length cannot be non-positive");
        }

        if (settings.getStuffedEnergy() <= settings.getReproduceEnergy()){
            throw new InvalidSettingsException("Stuffed energy cannot be less than reproduction energy");
        }
    }

    @FXML
    private void startSimulation() {
        try {
            SimulationSettings settings = prepareSettings();
            validateSettings(settings);

            Simulation simulation = new Simulation(settings);
        } catch (InvalidSettingsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void loadConfigFromFile(ActionEvent event) {
        try {
            Window window = ((Node) event.getTarget()).getScene().getWindow();
            File configFile = new FileChooser().showOpenDialog(window);
            Reader reader = Files.newBufferedReader(Paths.get(configFile.getAbsolutePath()));
            SimulationSettings settings = new Gson().fromJson(reader, SimulationSettings.class);
            reader.close();

            validateSettings(settings);

            Simulation simulation = new Simulation(settings);
            simulation.show();
        } catch (IOException | NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Config file not found");
            alert.show();
        } catch (JsonSyntaxException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Config file is corrupted");
            alert.show();
        } catch (InvalidSettingsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
