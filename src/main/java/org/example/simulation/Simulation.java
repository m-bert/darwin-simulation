package org.example.simulation;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.gui.controllers.SimulationController;
import org.example.maps.Globe;
import org.example.maps.HellPortal;
import org.example.maps.IMap;
import org.example.settings.SimulationSettings;
import org.example.settings.variants.MapVariant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Simulation extends Stage {
    private final SimulationSettings simulationSettings;
    private final SimulationController simulationController;
    private final Scene simulationScene;

    private final SimulationEngine simulationEngine;
    private final Thread simulationThread;

    private final String dataFilename;

    public Simulation(SimulationSettings simulationSettings) {
        this.simulationSettings = simulationSettings;
        IMap map;

        if(simulationSettings.isSaveEnabled()){
            dataFilename = System.getProperty("user.home") + File.separator + "simulation" + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()) + ".csv";
            File dataFile = new File(dataFilename);

            try {
                if(dataFile.createNewFile()){
                    BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));
                    writer.write("Animals;Plants;Free_places;Top_genomes;Average_energy;Average_lifetime");
                    writer.close();
                }
            } catch (IOException e) {
                simulationSettings.setSaveToCSV(false);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Couldn't create CSV file at:\n" + dataFilename);
            }
        } else {
            dataFilename = null;
        }

        if (simulationSettings.getMapVariant() == MapVariant.GLOBE) {
            map = new Globe(simulationSettings.getMapWidth(), simulationSettings.getMapHeight(), simulationSettings.getPlantsGrowth(),
                    simulationSettings.getInitialPlants(), simulationSettings.getPlantsEnergy(), simulationSettings.getPlantsGrowthVariant());
        } else {
            map = new HellPortal(simulationSettings.getMapWidth(), simulationSettings.getMapHeight(), simulationSettings.getPlantsGrowth(),
                    simulationSettings.getInitialPlants(), simulationSettings.getPlantsEnergy(), simulationSettings.getPlantsGrowthVariant());
        }

        simulationController = new SimulationController(simulationSettings, map);
        simulationScene = new Scene(simulationController);

        setScene(simulationScene);
        show();

        simulationEngine = new SimulationEngine(simulationSettings, simulationController, map, dataFilename);

        simulationThread = new Thread(simulationEngine);
        simulationThread.start();

        setOnCloseRequest(e -> simulationThread.stop());
    }
}
