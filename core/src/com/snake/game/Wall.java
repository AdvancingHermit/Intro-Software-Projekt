//Christian

package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.snake.game.util.Vector;

import java.util.HashSet;
import java.util.Set;

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

    public Set<Vector> getOccupiedTiles() {
        Set<Vector> result = new HashSet<>();
        result.add(getSnakePos());
        for (int i = 1; i < getSize().x; i++) {
            result.add(new Vector(getSnakePos().x + i, getSnakePos().y));
        }
        for (int i = 1; i < getSize().y; i++) {
            result.add(new Vector(getSnakePos().x, getSnakePos().y+i));

        }
        return result;
    }
}
