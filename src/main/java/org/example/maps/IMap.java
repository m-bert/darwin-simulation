package org.example.maps;

import org.example.elements.AbstractMapElement;
import org.example.settings.variants.MapVariant;
import org.example.utils.Vector2D;

import java.util.LinkedList;

public interface IMap {
    boolean isInsideBoundaries(Vector2D position);
    Vector2D teleportAnimal(Vector2D position);
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
