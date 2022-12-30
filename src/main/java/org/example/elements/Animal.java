package org.example.elements;

import org.example.utils.IPositionChangeObserver;
import org.example.utils.MapDirection;
import org.example.utils.Vector2D;

import java.util.ArrayList;

public class Animal extends AbstractMapElement{

    public int lifeDays;
    public int initialEnergy;
    public int reproduceEnergy;
    public int stuffedEnergy;
    public int moveEnergy;
    public int childrenNum;
    public Genome genome;
    public boolean isAlive;
    public MapDirection direction = MapDirection.NORTH;
    private final ArrayList<IPositionChangeObserver> observers;

    public Animal(Vector2D position, int genomeLength, int initialEnergy, int stuffedEnergy, int reproduceEnergy, int moveEnergy) {
        // initial position for animal
        super(position);
        this.direction.randomDirection();
        this.observers = new ArrayList<>();
        // when animal is dead - set false
        this.isAlive = true;
        this.lifeDays = 0;
        this.childrenNum = 0;
        // get genomeLength from settings
        this.genome = new Genome(genomeLength);
        // only first generation of animal have init energy from settings
        // juveniles will have init energy as reproduction energy from parrents
        this.initialEnergy = initialEnergy;
        // ready to reproduce energy
        this.stuffedEnergy = stuffedEnergy;
        // lost energy during reproduction
        this.reproduceEnergy = reproduceEnergy;
        this.moveEnergy = moveEnergy;

        // first animal random genome, juveniles genome from mutation
        genome.randomGenome();
    }

    private void positionChanged(Vector2D oldPosition, Vector2D newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public void rotateAnimal(int angleNumber){
        // angleNumber from 0 to 7 (map directions)
        for(int i=0; i < angleNumber; i++){
            direction = direction.next();
        }
    }

    public void move(){
        initialEnergy -= moveEnergy;
        if(initialEnergy < 0){
            isAlive = false;
        }
        else {
            int currentGenome = genome.next();
            rotateAnimal(currentGenome); // add from genome next gen
            Vector2D previousPosition = position;
            position = position.add(direction.toUnitVector());
            positionChanged(previousPosition, position);
        }
    }


    public String toString() {
        return direction.toString();
    }

    public MapDirection getDirection() {
        return direction;
    }

    public void addObserver(IPositionChangeObserver newObserver) {
        this.observers.add(newObserver);
    }

    public void removeObserver(IPositionChangeObserver observerToRemove) {
        this.observers.remove(observerToRemove);
    }

    @Override
    public String getPath() {
        return switch (getDirection()) {
            case NORTH -> "src/main/resources/up.png";
            case NORTHEAST -> "src/main/resources/rightup.png";
            case EAST -> "src/main/resources/right.png";
            case SOUTHEAST -> "src/main/resources/rightdown.png";
            case SOUTH -> "src/main/resources/down.png";
            case SOUTHWEST -> "src/main/resources/leftdown.png";
            case WEST -> "src/main/resources/left.png";
            case NORTHWEST -> "src/main/resources/leftup.png";
        };

    }
}

