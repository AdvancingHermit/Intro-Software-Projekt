package com.snake.game.util;

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

    public boolean equals(Vector other) {
        return (x == other.x && y == other.y);
    }
}