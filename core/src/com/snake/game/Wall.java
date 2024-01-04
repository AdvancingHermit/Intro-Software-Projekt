package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.snake.game.util.Vector;

public class Wall extends GameObject {

    Vector size;

    public Wall(Vector snakePosition, Texture sprite, Vector spritePos, Vector size) {
        super(snakePosition, sprite, spritePos);
        this.size = size;
    }

    public Vector getSize() {
        return size;
    }

    public int getNumberOfWalls(){
        return size.x + size.y - 1;
    }
}
