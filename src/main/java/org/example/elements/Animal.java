package org.example.elements;

import org.example.utils.MapDirection;
import org.example.utils.Vector2D;

import java.util.Map;

public class Animal extends AbstractMapElement{

    public int lifeDays;
    public int power;
    public int[] genom;
    public MapDirection direction;

    public Animal(Vector2D position) {
        super(position);
        this.direction = MapDirection.NORTH;
    }

    public String toString() {
        return direction.toString();
    }

    @Override
    public String getPath() {
        return super.getPath();

    }
}

