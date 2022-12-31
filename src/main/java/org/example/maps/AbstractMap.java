package org.example.maps;

import org.example.elements.AbstractMapElement;
import org.example.elements.Animal;
import org.example.settings.variants.PlantsGrowthVariant;
import org.example.utils.IPositionChangeObserver;
import org.example.utils.Vector2D;

import java.util.HashMap;
import java.util.LinkedList;

public class AbstractMap implements IMap, IPositionChangeObserver {
    protected final int WIDTH;
    protected final int HEIGHT;
    protected final HashMap<Vector2D, LinkedList<Animal>> animals;

    // Plants
    protected int plantsNum;
    protected final int plantsGrowth;
    protected final int initialPlants;
    protected final PlantsGrowthVariant plantsGrowthVariant;

    // Animals
    protected int animalsNum;


    @Override
    public void positionChanged(Vector2D oldPosition, Vector2D newPosition) {

    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        return false;
    }

    @Override
    public void moveCallback(Vector2D position) {

    }

    @Override
    public boolean place(AbstractMapElement mapElement) {
        return false;
    }

    @Override
    public LinkedList<AbstractMapElement> objectsAt(Vector2D position) {
        return null;
    }

    @Override
    public void removeDeadAnimals() {

    }

    @Override
    public void eating() {

    }

    @Override
    public void reproduce() {

    }

    @Override
    public void plantSeeds() {

    }
}
