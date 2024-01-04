package com.snake.game;

import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.snake.game.Snake;
import com.snake.game.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
    Snake[] snakes;
    Wall[] walls;
    int squareSize;
    int gridSize;

    Grid(int gridSize, int snakeAmount, int screenHeight) {
        snakes = new Snake[snakeAmount];
        System.out.println(gridSize + " " + screenHeight);
        squareSize = (screenHeight * 70) / 100 / gridSize;
        this.gridSize = gridSize;
        for (int i = 0; i < snakeAmount; i++) {
            snakes[i] = new Snake(gridSize, gridSize);
        }
        Wall[] walls = wallGenerator(gridSize);
    }

    public Rectangle[][] show(float game_width, float game_height) {
        for (int i = 0; i < snakes.length; i++) {
            if (!snakes[i].isDead) {
                snakes[i].move();
            }
        }
        double xOffset = ((game_width / 2) - (squareSize * gridSize) / 2);
        double yOffset = ((game_height / 2) - (squareSize * gridSize) / 2);

        Rectangle[][] rects = new Rectangle[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                rects[i][j] = new Rectangle(i * squareSize + (int) xOffset, j * squareSize + (int) yOffset, squareSize,
                        squareSize);
            }
        }
        return rects;
    }

    public Wall[] wallGenerator(int gridSize) {
        Random random = new Random();
        Wall[] walls = new Wall[gridSize / 5];

        for (int i = 0; i < walls.length; i++) {
            int posX = random.nextInt(gridSize);
            int posY = random.nextInt(gridSize);
            walls[i] = new Wall(new Vector(posX, posY), null, new Vector(posX * squareSize - (gridSize/2) * squareSize, posY * squareSize - (gridSize/2) * squareSize), new Vector(random.nextInt(1, 5), random.nextInt(1, 5)));
        }
        return walls;
    }

}
