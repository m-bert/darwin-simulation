package org.example.elements;

import org.example.utils.Vector2D;

public class Grass extends AbstractMapElement{

    public Grass(Vector2D position){
        super(position);
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public String getPath() {
        return "src/main/resources/gfx/grass.png";
    }
}
