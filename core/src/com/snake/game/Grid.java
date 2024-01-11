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

    private int snakeAmount;

    Grid(Vector gridSize, int snakeAmount, int screenHeight) {
        this.snakeAmount = snakeAmount;
        snakes = new Snake[snakeAmount];
        System.out.println(gridSize + " " + screenHeight);
        int max = Math.max(gridSize.x, gridSize.y);
        squareSize = (screenHeight * 70) / 100 / max;
        this.gridSize = gridSize;
        
        
        int[] keys = new int[] { Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D };
        if (snakeAmount == 1) {
            snakes[0] = new Snake(gridSize.x / 2, gridSize.y / 2, keys);
            return;
        } 
        for (int i = 0; i < snakeAmount; i++) {
            snakes[i] = new Snake(gridSize.x / 2, -2 + i*2 + gridSize.y / 2, keys);
            if (i == 0) {
                keys = new int[] { Input.Keys.UP, Input.Keys.LEFT, Input.Keys.DOWN, Input.Keys.RIGHT};
            } 
            else if (i == 1) {
                keys = new int[] { Input.Keys.I, Input.Keys.J, Input.Keys.K, Input.Keys.L };
            }
        }

    }

    public Rectangle[][] show(float game_width, float game_height) {
        for (Snake snake : snakes) {
            if (!snake.isDead) {
                snake.move();
                checkSnakeCollision(snakes, snake);
            }
        }

        double xOffset = ((game_width / 2) - (squareSize * gridSize.x) / 2);
        double yOffset = ((game_height / 2) - (squareSize * gridSize.y) / 2);

        Rectangle[][] rects = new Rectangle[gridSize.x][gridSize.y];
        for (int i = 0; i < gridSize.x; i++) {
            for (int j = 0; j < gridSize.y; j++) {
                rects[i][j] = new Rectangle(i * squareSize + (int) xOffset, j * squareSize + (int) yOffset, squareSize,
                        squareSize);
            }
        }
        return rects;
    }

    public Wall[] wallGenerator(Vector gridSize) {
        Random random = new Random();
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
        }
        return walls;
    }

    public void checkSnakeCollision(Snake[] snakes, Snake currSnake) {
		for (Snake otherSnake : snakes) {
			if (otherSnake != currSnake) {
				ArrayList<Vector> positions = otherSnake.getPositions();
                Vector head = currSnake.getPositions().get(currSnake.getPositions().size() - 1);
                Vector otherHead = otherSnake.getPositions().get(otherSnake.getPositions().size() - 1);
                for (Vector position : positions) {
                    if (head.equals(position)) {
                        currSnake.isDead = true;
                        if (head.equals(otherHead)){
                            otherSnake.isDead = true;
                        }
                        return;
                    }


                }
                if (otherSnake.fireActive) {
                        otherHead = otherSnake.getPositions().get(otherSnake.getPositions().size() - 1);
                        Vector otherVel = otherSnake.getVel();
                        if (currSnake.checkCollision(new Vector(otherHead.x + otherVel.x, otherHead.y + otherVel.y)) ||
							currSnake.checkCollision(new Vector(otherHead.x + otherVel.x * 2, otherHead.y + otherVel.y * 2))) {
                            currSnake.isDead = true;
                        }
			}   }
		}

    }

}
