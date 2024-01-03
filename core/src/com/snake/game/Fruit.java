package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.snake.game.util.Vector;

public class Fruit {

    private Vector position;
    private Texture sprite;

    boolean isOnScreen;

    public Fruit(Vector position, Texture sprite){
        this.position = position;
        this.sprite = sprite;
    }

    public Vector getPosition() {
        return position;
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
