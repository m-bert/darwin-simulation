package org.example.simulation;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.SimulationController;
import org.example.maps.Globe;
import org.example.maps.HellPortal;
import org.example.maps.IMap;
import org.example.settings.SimulationSettings;
import org.example.settings.variants.MapVariant;

public class Simulation extends Stage {
    private final SimulationSettings simulationSettings;
    private final SimulationController simulationController;
    private final Scene simulationScene;

    private final SimulationEngine simulationEngine;
    private final Thread simulationThread;


    public Simulation(SimulationSettings simulationSettings) {
        this.simulationSettings = simulationSettings;
        IMap map;

        if (simulationSettings.getMapVariant() == MapVariant.GLOBE) {
            map = new Globe(simulationSettings.getMapWidth(), simulationSettings.getMapHeight(), simulationSettings.getPlantsGrowth(),
                    simulationSettings.getInitialPlants(), simulationSettings.getPlantsEnergy(), simulationSettings.getPlantsGrowthVariant());
        } else {
            map = new HellPortal(simulationSettings.getMapWidth(), simulationSettings.getMapHeight(), simulationSettings.getPlantsGrowth(),
                    simulationSettings.getInitialPlants(), simulationSettings.getPlantsEnergy(), simulationSettings.getPlantsGrowthVariant());
        }

        simulationEngine = new SimulationEngine(simulationSettings, map);

        simulationController = new SimulationController(new Gson().toJson(simulationSettings));
        simulationScene = new Scene(simulationController);
        this.setScene(simulationScene);

        simulationThread = new Thread(simulationEngine);
        simulationThread.start();

        setOnCloseRequest(e -> simulationThread.stop());
    }
}
