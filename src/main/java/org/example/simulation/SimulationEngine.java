package org.example.simulation;

import javafx.application.Platform;
import org.example.elements.Animal;
import org.example.maps.IMap;
import org.example.settings.SimulationSettings;
import org.example.utils.Vector2D;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationEngine implements Runnable, ISimulationEngine {
    private final SimulationSettings settings;
    private final ISimulationController simulationController;
    private final IMap map;
    private final File dataFile;
    private boolean isRunning;

    public SimulationEngine(SimulationSettings settings, ISimulationController simulationController, IMap map, String dataFilename) {
        this.settings = settings;
        this.map = map;
        this.simulationController = simulationController;
        isRunning = true;

        if (dataFilename != null) {
            dataFile = new File(dataFilename);
        } else {
            dataFile = null;
        }

        placeAnimals();
        simulationController.update();
    }

    void placeAnimals() {
        for (int i = 0; i < settings.getInitialAnimals(); ++i) {
            int x = ThreadLocalRandom.current().nextInt(0, settings.getMapWidth());
            int y = ThreadLocalRandom.current().nextInt(0, settings.getMapHeight());
            Vector2D position = new Vector2D(x, y);

            Animal animal = new Animal(
                    map.getCurrentId(), position, settings.getGenomeLength(), settings.getInitialEnergy(), settings.getStuffedEnergy(),
                    settings.getReproduceEnergy(), settings.getMoveEnergy(), settings.getMutationVariant(), settings.getBehaviourVariant(),
                    map
            );

            map.place(animal);
        }
    }

    void moveAnimals() {
        ConcurrentHashMap<Vector2D, LinkedList<Animal>> animals = map.getAnimals();
        LinkedList<Animal> allAnimals = new LinkedList<>();

        for (Vector2D key : animals.keySet()) {
            allAnimals.addAll(animals.get(key));
        }

        for (Animal animal : allAnimals) {
            animal.move();
        }
    }

    private void saveDay() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile, true));
            writer.append("\n")
                    .append(String.valueOf(map.getStatistics().getAnimalsAmount())).append(";")
                    .append(String.valueOf(map.getStatistics().getPlantsAmount())).append(";")
                    .append(String.valueOf(map.getStatistics().getFreeCellsAmount())).append(";")
                    .append(String.join(",", map.getStatistics().getTopGenomes())).append(";")
                    .append(String.valueOf(map.getStatistics().getAverageEnergy())).append(";")
                    .append(String.valueOf(map.getStatistics().getAverageDeadAnimalsLifeLength())).append(";");

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (map.getAnimalsNum() > 0) {
            map.removeDeadAnimals();
            moveAnimals();
            map.eating();
            map.reproduce();
            map.plantSeeds();

            map.updateStatistics();

            synchronized (this) {
                if (!isRunning) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (dataFile != null) {
                saveDay();
            }

            try {
                Platform.runLater(simulationController::update);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void pause() {
        isRunning = false;
    }

    @Override
    public synchronized void resume() {
        isRunning = true;
        notify();
    }
}
