package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.snake.game.util.Vector;

public class Fruit {

    private Vector snakePos;
    private Texture sprite;

    private Vector spritePos;

    boolean isOnScreen;


    public Vector getSpritePos() {
        return spritePos;
    }

    public void setSpritePos(Vector spritePos) {
        this.spritePos = spritePos;
    }

    public Fruit(Vector snakePosition, Texture sprite, Vector spritePos){
        this.snakePos = snakePosition;
        this.sprite = sprite;
        this.spritePos = spritePos;
    }

    public Vector getSnakePos() {
        return snakePos;
    }

    public boolean isOnScreen() {
        return isOnScreen;
    }

    public void hasPickedUpFruit() {
        isOnScreen = false;
    }

    public Texture getSprite() {
        return sprite;
    }
}
