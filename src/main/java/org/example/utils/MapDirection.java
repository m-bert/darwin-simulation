package org.example.utils;

import java.util.Random;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    final private static MapDirection[] directVals = values();
    private static final Random generator = new Random();

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "^";
            case NORTHEAST -> "/";
            case EAST -> ">";
            case SOUTHEAST -> "\\";
            case SOUTH -> "v";
            case SOUTHWEST -> "/";
            case WEST -> "<";
            case NORTHWEST -> "\\";
        };
    }

    public MapDirection next() {
        return directVals[(this.ordinal()+1) % directVals.length];
    }

    public MapDirection previous() {
        return directVals[(this.ordinal()-1 + directVals.length) % directVals.length];
    }

    public MapDirection randomDirection(){
        return directVals[generator.nextInt(directVals.length)];
    }

    public Vector2D toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2D(0, 1);
            case NORTHEAST -> new Vector2D(1, 1);
            case EAST -> new Vector2D(1, 0);
            case SOUTHEAST -> new Vector2D(1, -1);
            case SOUTH -> new Vector2D(0, -1);
            case SOUTHWEST -> new Vector2D(-1, -1);
            case WEST -> new Vector2D(-1, 0);
            case NORTHWEST -> new Vector2D(-1, 1);
        };
    }
}
