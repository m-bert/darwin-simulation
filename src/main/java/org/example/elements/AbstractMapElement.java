package org.example.elements;

import org.example.utils.Vector2D;

public class AbstractMapElement implements IMapElement {

    protected Vector2D position;

    public AbstractMapElement(Vector2D position) {
        this.position = position;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public String getPath() {
        return null;
    }
}
