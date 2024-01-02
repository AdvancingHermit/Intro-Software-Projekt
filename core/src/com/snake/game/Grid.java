package com.snake.game;

import com.badlogic.gdx.math.Rectangle;
import com.snake.game.Snake;
import java.util.ArrayList;

public class Grid {
    Snake[] snakes;
    int snakeSize;

    Grid(int snakeAmount){
        snakes = new Snake[snakeAmount];
        snakeSize = 30;

        for(int i = 0; i < snakeAmount; i++){
            snakes[i] = new Snake(100, 100);
        }
    }

    public Rectangle[][] show(){
        
        Rectangle[][] bodies = new Rectangle[snakes.length][];
        for(int i = 0; i < snakes.length; i++){
            snakes[i].move();

            bodies[i] = new Rectangle[snakes[i].getPositions().size()];

            for(int j = 0; j < bodies[i].length; j++){
                bodies[i][j] = new Rectangle();
                System.out.println(i + " hello " + j);
                bodies[i][j].x = snakes[i].getPositions().get(j).x + i * snakeSize;
                bodies[i][j].y = snakes[i].getPositions().get(j).y + j * snakeSize;
            }
        }
        return bodies;
    }
    
}
