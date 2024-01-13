package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.snake.game.util.Vector;

//Made by Oliver
//the fruit that gets drawn on screen
public class Fruit extends GameObject {
    private int score;
    private int growth;

    public Fruit(Vector snakePosition, Texture sprite, Vector spritePos, int score, int growth) {
        super(snakePosition, sprite, spritePos);
        this.score = score;
        this.growth = growth;

    }

    public int getScore() {
        return score;
    }

    public int getGrowth() {
        return growth;
    }
}
