package com.snake.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.snake.game.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
    Snake[] snakes;
    Wall[] walls;
    int squareSize;
    Vector gridSize;
    Vector offset;
   

    private int snakeAmount;

    // Primarily made by Oscar
    Grid(Vector gridSize, int snakeAmount, int screenHeight, int maxcounter) {
        this.snakeAmount = snakeAmount;
        snakes = new Snake[snakeAmount];
        int max = Math.max(gridSize.x, gridSize.y);
        squareSize = (screenHeight * 70) / 100 / max;
        this.gridSize = gridSize;

        int[] keys = new int[] { Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D };
        for (int i = 0; i < snakeAmount; i++) {
            snakes[i] = new Snake(gridSize.x / 2, -2 + i * 2 + gridSize.y / 2, maxcounter,  keys);
            if (i == 0) {
                keys = new int[] { Input.Keys.UP, Input.Keys.LEFT, Input.Keys.DOWN, Input.Keys.RIGHT };
            } else if (i == 1) {
                keys = new int[] { Input.Keys.I, Input.Keys.J, Input.Keys.K, Input.Keys.L };
            }
        }

    }
    //Made by Oscar
    public Rectangle[][] show(float game_width, float game_height) {
        for (Snake snake : snakes) {
            if (!snake.isDead) {
                snake.move();

                checkSnakeCollision(snakes, snake);
            }
        }

        offset = new Vector( (int) ((game_width / 2) - (squareSize * gridSize.x) / 2), (int) ((game_height / 2) - (squareSize * gridSize.y) / 2));

        Rectangle[][] rects = new Rectangle[gridSize.x][gridSize.y];
        for (int i = 0; i < gridSize.x; i++) {
            for (int j = 0; j < gridSize.y; j++) {
                rects[i][j] = new Rectangle(i * squareSize + (int) offset.x, j * squareSize + (int) offset.y, squareSize,
                        squareSize);
            }
        }
        return rects;
    }

    //Christian
    public Wall[] wallGenerator(Vector gridSize) {
        Random random = new Random(System.currentTimeMillis());
        Wall[] walls = new Wall[(int) Math.sqrt(gridSize.x * gridSize.y) / 5];

        for (int i = 0; i < walls.length; i++) {
            int posX = random.nextInt(gridSize.x);
            int posY = random.nextInt(gridSize.y);

            walls[i] = new Wall(new Vector(posX, posY), null,
                    new Vector((int) (posX * squareSize - (gridSize.x / 2.0) * squareSize),
                            (int) (posY * squareSize - (gridSize.y / 2.0) * squareSize)),
                    new Vector(random.nextInt(1, 5), random.nextInt(1, 5)));

            if (walls[i].size.x + posX > gridSize.x) {
                walls[i].size.x = gridSize.x - posX;
            }
            if (walls[i].size.y + posY > gridSize.y) {
                walls[i].size.y = gridSize.y - posY;
            }
            for (int l = 0; l < walls[i].size.x; l++) {
                for (int j = 0; j < snakeAmount; j++) {
                    for (int k = 0; k < snakes[j].getPositions().size(); k++) {
                        if (walls[i].getSnakePos().add(new Vector(l, 0)).equals(snakes[j].getPositions().get(k))) {
                            walls[i].size = new Vector(0, 0);
                        }
                        
                    }
                    if (walls[i].getSnakePos().add(new Vector(l, 0))
                                .equals((snakes[j].getPositions().get(snakes[j].getPositions().size() - 1).add(new Vector(-1, 0))))) {
                            walls[i].size = new Vector(0, 0);
                    }
                }
            }
            for (int l = 0; l < walls[i].size.y; l++) {
                for (int j = 0; j < snakeAmount; j++) {
                    for (int k = 0; k < snakes[j].getPositions().size(); k++) {
                        if (walls[i].getSnakePos().add(new Vector(0, l)).equals(snakes[j].getPositions().get(k))) {
                            walls[i].size = new Vector(0, 0);
                        }
                        
                    }
                }
            }

        }
        return walls;
    }
    //Made by Oscar
    //Checks if the given currSnake collides with another snake
    public void checkSnakeCollision(Snake[] snakes, Snake currSnake) {
        for (Snake otherSnake : snakes) {
            if (otherSnake != currSnake) {
                ArrayList<Vector> positions = otherSnake.getPositions();
                Vector head = currSnake.getPositions().get(currSnake.getPositions().size() - 1);
                Vector otherHead = otherSnake.getPositions().get(otherSnake.getPositions().size() - 1);
                for (Vector position : positions) {
                    if (head.equals(position)) {
                        currSnake.isDead = true;
                        if (head.equals(otherHead)) {
                            otherSnake.isDead = true;
                        }
                        return;
                    }

                }
                if (otherSnake.fireActive) {
                    otherHead = otherSnake.getPositions().get(otherSnake.getPositions().size() - 1);
                    Vector otherVel = otherSnake.getVel();
                    if (currSnake.checkCollision(new Vector(otherHead.x + otherVel.x, otherHead.y + otherVel.y)) ||
                            currSnake.checkCollision(
                                    new Vector(otherHead.x + otherVel.x * 2, otherHead.y + otherVel.y * 2))) {
                        currSnake.isDead = true;
                    }
                }
            }
        }

    }

}
