package org.example.utils;

import org.example.elements.Animal;


public interface IAnimalObserver {
    void positionChanged(Animal animal, Vector2D oldPosition, Vector2D newPosition);

    void notifyDeath(Animal animal);
}
