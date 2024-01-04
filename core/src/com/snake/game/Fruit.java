package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.snake.game.util.Vector;

public class Fruit extends GameObject
{
    public Fruit(Vector snakePosition, Texture sprite, Vector spritePos) {
        super(snakePosition, sprite, spritePos);
    }
}
