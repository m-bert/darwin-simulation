package org.example.settings;

import org.example.settings.variants.BehaviourVariant;
import org.example.settings.variants.MapVariant;
import org.example.settings.variants.MutationVariant;
import org.example.settings.variants.PlantsGrowthVariant;

public class SimulationSettings {
    private MapVariant mapVariant;
    private PlantsGrowthVariant plantsGrowthVariant;
    private MutationVariant mutationVariant;
    private BehaviourVariant behaviourVariant;
    private int mapWidth;
    private int mapHeight;
    private int initialPlants;
    private int plantsEnergy;
    private int plantsGrowth;
    private int initialAnimals;
    private int initialEnergy;
    private int moveEnergy;
    private int stuffedEnergy;
    private int reproduceEnergy;
    private int minMutations;
    private int maxMutations;
    private int genomeLength;
    private boolean saveToCSV;

    public MapVariant getMapVariant() {
        return mapVariant;
    }

    public void setMapVariant(MapVariant mapVariant) {
        this.mapVariant = mapVariant;
    }

    public PlantsGrowthVariant getPlantsGrowthVariant() {
        return plantsGrowthVariant;
    }

    public void setPlantsGrowthVariant(PlantsGrowthVariant plantsGrowthVariant) {
        this.plantsGrowthVariant = plantsGrowthVariant;
    }

    public MutationVariant getMutationVariant() {
        return mutationVariant;
    }

    public void setMutationVariant(MutationVariant mutationVariant) {
        this.mutationVariant = mutationVariant;
    }

    public BehaviourVariant getBehaviourVariant() {
        return behaviourVariant;
    }

    public void setBehaviourVariant(BehaviourVariant behaviourVariant) {
        this.behaviourVariant = behaviourVariant;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getInitialPlants() {
        return initialPlants;
    }

    public void setInitialPlants(int initialPlants) {
        this.initialPlants = initialPlants;
    }

    public int getPlantsEnergy() {
        return plantsEnergy;
    }

    public void setPlantsEnergy(int plantsEnergy) {
        this.plantsEnergy = plantsEnergy;
    }

    public int getPlantsGrowth() {
        return plantsGrowth;
    }

    public void setPlantsGrowth(int plantsGrowth) {
        this.plantsGrowth = plantsGrowth;
    }

    public int getInitialAnimals() {
        return initialAnimals;
    }

    public void setInitialAnimals(int initialAnimals) {
        this.initialAnimals = initialAnimals;
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public void setInitialEnergy(int initialEnergy) {
        this.initialEnergy = initialEnergy;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public void setMoveEnergy(int moveEnergy) {
        this.moveEnergy = moveEnergy;
    }

    public int getStuffedEnergy() {
        return stuffedEnergy;
    }

    public void setStuffedEnergy(int stuffedEnergy) {
        this.stuffedEnergy = stuffedEnergy;
    }

    public int getReproduceEnergy() {
        return reproduceEnergy;
    }

    public void setReproduceEnergy(int reproduceEnergy) {
        this.reproduceEnergy = reproduceEnergy;
    }

    public int getMinMutations() {
        return minMutations;
    }

    public void setMinMutations(int minMutations) {
        this.minMutations = minMutations;
    }

    public int getMaxMutations() {
        return maxMutations;
    }

    public void setMaxMutations(int maxMutations) {
        this.maxMutations = maxMutations;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    public boolean isSaveEnabled() {
        return saveToCSV;
    }

    public void setSaveToCSV(boolean saveToCSV) {
        this.saveToCSV = saveToCSV;
    }
}
