package com.snake.game;

import com.badlogic.gdx.math.Rectangle;
import com.snake.game.Snake;
import com.snake.game.util.Vector;

import java.util.ArrayList;

public class Grid {
    Snake[] snakes;
    int snakeSize;
    int gridSize;

    Grid(int gridSize, int snakeAmount) {
        snakes = new Snake[snakeAmount];
        snakeSize = 30;
        this.gridSize = gridSize;
        for (int i = 0; i < snakeAmount; i++) {
            snakes[i] = new Snake(gridSize, gridSize);
        }

    }

    public Rectangle[][] show() {

        for (int i = 0; i < snakes.length; i++) {
            
            if(!snakes[i].isDead) {
                snakes[i].move();
                
            }
        }
        double xOffset = (1920 / 2) - (snakeSize*gridSize*0.6);
        double yOffset = (1080 / 2) - (snakeSize*gridSize* 0.6 );
        Rectangle[][] rects = new Rectangle[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                rects[i][j] = new Rectangle((int)(i * snakeSize*1.2 + xOffset), (int)(j * snakeSize*1.2 + yOffset), snakeSize, snakeSize);
            }
        }
        return rects;
    }

}
