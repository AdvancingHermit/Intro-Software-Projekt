package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.snake.game.util.Vector;

import java.util.Random;

public class Grid {
    Snake[] snakes;
    Wall[] walls;
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
        Wall[] walls = wallGenerator(gridSize);

        
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

    public Wall[] wallGenerator(Vector gridSize) {
        Random random = new Random();
        Wall[] walls = new Wall[(int)Math.sqrt(gridSize.x * gridSize.y) / 5];

        for (int i = 0; i < walls.length; i++) {
            int posX = random.nextInt(gridSize.x);
            int posY = random.nextInt(gridSize.y);

            walls[i] = new Wall(new Vector(posX, posY), null, new Vector(posX * squareSize - (gridSize.x/2) * squareSize, posY * squareSize - (gridSize.y/2) * squareSize), new Vector(random.nextInt(1, 5), random.nextInt(1, 5)));
        }
        return walls;
    }

}
