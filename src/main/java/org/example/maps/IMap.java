package org.example.maps;

import org.example.elements.AbstractMapElement;
import org.example.utils.Vector2D;

import java.util.LinkedList;

public interface IMap {
    boolean canMoveTo(Vector2D position);
    void moveCallback(Vector2D position);
    boolean place(AbstractMapElement mapElement);
    LinkedList<AbstractMapElement> objectsAt(Vector2D position);
    int getCurrentId();
    boolean containsGrassAt(Vector2D position);

    // Day cycle
    void removeDeadAnimals();
    void eating();
    void reproduce();
    void plantSeeds();
    void plantEquator();
    void plantCorpses();
}
