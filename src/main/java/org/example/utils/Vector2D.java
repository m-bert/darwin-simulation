package org.example.utils;

import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Vector2D {
    public final int x;
    public final int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean precedes(Vector2D other) {
        return other.x >= this.x && other.y >= this.y;
    }

    public boolean follows(Vector2D other) {
        return other.x <= this.x && other.y <= this.y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D upperRight(Vector2D other) {
        return new Vector2D(max(this.x, other.x), max(this.y, other.y));
    }

    public Vector2D lowerLeft(Vector2D other) {
        return new Vector2D(min(this.x, other.x), min(this.y, other.y));
    }

    public Vector2D opposite() {
        return new Vector2D(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2D vector2d)) return false;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
