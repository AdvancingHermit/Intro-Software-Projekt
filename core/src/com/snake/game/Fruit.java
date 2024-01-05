package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.snake.game.util.Vector;

public class Fruit extends GameObject {
    private int score;
    private int size;
    private boolean golden;
    public Fruit(Vector snakePosition, Texture sprite, Vector spritePos, int score, int size, boolean golden) {
        super(snakePosition, sprite, spritePos);
        this.score = score;
        this.size = size;
        this.golden = golden;
    }

    public boolean isGolden() {
        return golden;
    }
}
