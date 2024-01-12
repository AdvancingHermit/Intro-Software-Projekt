package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.snake.game.util.Vector;

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
