package org.example.utils.statistics;

import org.example.elements.Animal;

import java.util.List;

public class AnimalStatistics {
    private final Animal animal;
    private String genome;
    private int activeGenome;
    private int currentEnergy;
    private int grassEatenNum;
    private int childrenNum;
    private int age;
    private int deathDay;

    public AnimalStatistics(Animal animal) {
        this.animal = animal;
    }

    public void updateStatistics(){
        updateGenome();
        updateActiveGenome();
        updateCurrentEnergy();
        updateGrassEatenNum();
        updateChildrenNum();
        updateAge();
        updateDeathDay();
    }

    private void updateGenome(){
        genome = animal.getGenome().getGenomeStr();
    }

    private void updateActiveGenome(){
        activeGenome = animal.getGenome().currentGenome();
    }

    private void updateCurrentEnergy(){
        currentEnergy = animal.getEnergy();
    }

    private void updateGrassEatenNum(){
        grassEatenNum = animal.getGrassEatenNum();
    }

    private void updateChildrenNum(){
        childrenNum = animal.getChildrenNum();
    }

    private void updateAge(){
        age = animal.getLifeDays();
    }

    private void updateDeathDay(){
        if(animal.isAlive()){
            deathDay = -1;
        } else {
            deathDay = age;
        }
    }


    public String getGenome() {
        return genome;
    }

    public int getActiveGenome() {
        return activeGenome;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public int getGrassEatenNum() {
        return grassEatenNum;
    }

    public int getChildrenNum() {
        return childrenNum;
    }

    public int getAge() {
        return age;
    }

    public int getDeathDay(){
        return deathDay;
    }
}
