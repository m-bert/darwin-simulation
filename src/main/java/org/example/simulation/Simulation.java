package org.example.simulation;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.SimulationController;
import org.example.settings.SimulationSettings;

public class Simulation extends Stage {
    private final SimulationSettings simulationSettings;
    private final SimulationController simulationController;
    private final Scene simulationScene;


    public Simulation(SimulationSettings simulationSettings) {
        this.simulationSettings = simulationSettings;

        simulationController = new SimulationController(new Gson().toJson(simulationSettings));
        simulationScene = new Scene(simulationController);

        this.setScene(simulationScene);
    }
}
