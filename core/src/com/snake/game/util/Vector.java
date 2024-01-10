package com.snake.game.util;

import java.util.Objects;

public class Vector{
    public int x;
    public int y;

    public Vector(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }
    public Vector mult(int scalar) {
        return new Vector(x * scalar, y * scalar);
    }
  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return x == vector.x && y == vector.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
  
    @Override
    public String toString(){
        return "X: " + x + " Y:" + y;
    }

    public double angle() {
        return Math.toDegrees(Math.atan2(y, x));
    }

    public double mag() {
        return Math.sqrt(x * x + y * y);
    }
}