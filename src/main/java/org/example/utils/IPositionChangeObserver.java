package org.example.utils;

import org.example.elements.Animal;


//TODO: Rename this interface
public interface IPositionChangeObserver {
    void positionChanged(Animal animal, Vector2D oldPosition, Vector2D newPosition);
    void notifyDeath(Animal animal);
}
