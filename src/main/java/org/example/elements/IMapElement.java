package org.example.elements;

import org.example.utils.Vector2D;

public interface IMapElement{

    // current position
    Vector2D getPosition();

    // path to image
    String getPath();

    String toString();
}