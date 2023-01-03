package org.example.utils.statistics;

import org.example.elements.Animal;
import org.example.maps.IMap;
import org.example.utils.Vector2D;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MapStatistics {
    private final IMap map;

    // Statistic fields
    private int animalsAmount;
    private int plantsAmount;
    private int freeCellsAmount;
    private final ArrayList<String> topGenomes;
    private final int TOP_GENOMES_NUM;
    private double averageEnergy;
    private double averageDeadAnimalsLifeLength;

    public MapStatistics(IMap map) {
        this.map = map;
        topGenomes = new ArrayList<>();
        TOP_GENOMES_NUM = 5;
    }

    public void updateStatistics() {
        updateAnimalsAmount();
        updatePlantsAmount();
        updateFreeCellsAmount();
        updateTopGenomes();
        updateAverageEnergy();
        updateAverageDeadAnimalsLifeLength();
    }

    private void updateAnimalsAmount() {
        animalsAmount = map.getAnimalsNum();
    }

    private void updatePlantsAmount() {
        plantsAmount = map.getGrass().size();
    }

    private void updateFreeCellsAmount() {
        ConcurrentHashMap<Vector2D, LinkedList<Animal>> animals = map.getAnimals();
        int animalFieldsWithoutGrass = 0;

        for (Vector2D key : animals.keySet()) {
            if (!map.containsGrassAt(key)) {
                ++animalFieldsWithoutGrass;
            }
        }

        freeCellsAmount = map.getSize() - map.getGrass().size() - animalFieldsWithoutGrass;
    }

    private void updateTopGenomes() {
        topGenomes.clear();

        ConcurrentHashMap<Vector2D, LinkedList<Animal>> animals = map.getAnimals();
        ArrayList<String> genomes = new ArrayList<>();

        for (Vector2D key : animals.keySet()) {
            LinkedList<Animal> animalsAt = animals.get(key);

            for (Animal animal : animalsAt) {
                genomes.add(animal.getGenome().getGenomeStr());
            }
        }

        Map<String, Integer> countMap = new TreeMap<>();
        for (String genome : genomes) {
            if (countMap.containsKey(genome)) {
                countMap.put(genome, countMap.get(genome) + 1);
            } else {
                countMap.put(genome, 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(countMap.entrySet());
        sortedList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        for (int i = 0; i < TOP_GENOMES_NUM; ++i) {
            if (i == sortedList.size()) {
                break;
            }

            Map.Entry<String, Integer> entry = sortedList.get(i);
            topGenomes.add(entry.getKey());
        }
    }

    private void updateAverageEnergy() {
        ConcurrentHashMap<Vector2D, LinkedList<Animal>> animals = map.getAnimals();
        int totalEnergy = 0;

        for (Vector2D key : animals.keySet()) {
            LinkedList<Animal> animalsAt = animals.get(key);

            for (Animal animal : animalsAt) {
                totalEnergy += animal.getEnergy();
            }
        }

        averageEnergy = (double) totalEnergy / map.getAnimalsNum();
    }

    private void updateAverageDeadAnimalsLifeLength() {
        ArrayList<Animal> deadAnimals = map.getDeadAnimalsHistory();

        int totalLifeLength = 0;

        for (Animal animal : deadAnimals) {
            totalLifeLength += animal.getLifeDays();
        }

        averageDeadAnimalsLifeLength = (double) totalLifeLength / deadAnimals.size();
    }

    public int getAnimalsAmount() {
        return animalsAmount;
    }

    public int getPlantsAmount() {
        return plantsAmount;
    }

    public int getFreeCellsAmount() {
        return freeCellsAmount;
    }

    public ArrayList<String> getTopGenomes() {
        return topGenomes;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public double getAverageDeadAnimalsLifeLength() {
        return averageDeadAnimalsLifeLength;
    }
}
