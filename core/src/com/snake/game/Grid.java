package com.snake.game;

import com.badlogic.gdx.math.Rectangle;
import com.snake.game.util.Vector;

public class Grid {
    Snake[] snakes;
    int squareSize;
    Vector gridSize;

    Grid(Vector gridSize, int snakeAmount, int screenHeight) {
        snakes = new Snake[snakeAmount];
        System.out.println(gridSize + " " + screenHeight);
        int max = Math.max(gridSize.x, gridSize.y);
        squareSize = (screenHeight * 70) / 100 / max;
        this.gridSize = gridSize;
        for (int i = 0; i < snakeAmount; i++) {
            snakes[i] = new Snake(gridSize.x, gridSize.y);
        }
    }

    public Rectangle[][] show(float game_width, float game_height) {
        for (int i = 0; i < snakes.length; i++) {
            if (!snakes[i].isDead) {
                snakes[i].move();
            }
        }
        double xOffset = ((game_width / 2) - (squareSize*gridSize.x) / 2);
        double yOffset = ((game_height / 2) - (squareSize*gridSize.y) / 2);

        Rectangle[][] rects = new Rectangle[gridSize.x][gridSize.y];
        for (int i = 0; i < gridSize.x; i++) {
            for (int j = 0; j < gridSize.y; j++) {
                rects[i][j] = new Rectangle(i * squareSize + (int)xOffset, j * squareSize + (int)yOffset, squareSize, squareSize);
            }
        }
        return rects;
    }

}
