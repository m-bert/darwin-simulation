package org.example.maps;

import org.example.elements.AbstractMapElement;
import org.example.elements.Animal;
import org.example.elements.Grass;
import org.example.utils.statistics.MapStatistics;
import org.example.utils.Vector2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public interface IMap {
    boolean isInsideBoundaries(Vector2D position);
    Vector2D teleportAnimal(Vector2D position);
    boolean place(AbstractMapElement mapElement);
    LinkedList<AbstractMapElement> objectsAt(Vector2D position);
    int getCurrentId();
    boolean containsGrassAt(Vector2D position);
    int getAnimalsNum();
    ConcurrentHashMap<Vector2D, LinkedList<Animal>> getAnimals();
    ArrayList<Animal> getDeadAnimalsHistory();
    ConcurrentHashMap<Vector2D, Grass> getGrass();
    int getSize();

    // Day cycle
    void removeDeadAnimals();
    void eating();
    void reproduce();
    void plantSeeds();
    void plantEquator();
    void plantCorpses();

    void updateStatistics();
    MapStatistics getStatistics();
}
