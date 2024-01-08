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
        snakes[0] = new Snake(gridSize.x / 2, gridSize.y / 2);

        int[] keys = { Input.Keys.UP, Input.Keys.LEFT, Input.Keys.DOWN, Input.Keys.RIGHT };
        for (int i = 1; i < snakeAmount; i++) {

            snakes[i] = new Snake(i * 3 + gridSize.x / 2, gridSize.y / 2, keys);
        }

    }

    public Rectangle[][] show(float game_width, float game_height) {
        for (int i = 0; i < snakes.length; i++) {
            if (!snakes[i].isDead) {
                snakes[i].move();
                checkSnakeCollision(snakes, snakes[i]);
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

    public boolean checkSnakeCollision(Snake[] snakes, Snake currSnake) {
		for (Snake otherSnake : snakes) {
			if (otherSnake != currSnake) {
				ArrayList<Vector> positions = otherSnake.getPositions();
                Vector head = currSnake.getPositions().get(currSnake.getPositions().size() - 1);
				for (int i = 0; i < positions.size(); i++) {
					if (head.equals(positions.get(i))) {
						currSnake.isDead = true;
						return true;
					}
                    

				}
                if (otherSnake.fireActive) {
                        Vector otherHead = otherSnake.getPositions().get(otherSnake.getPositions().size() - 1);
                        Vector otherVel = otherSnake.getVel();
                        if (currSnake.checkCollision(new Vector(otherHead.x + otherVel.x, otherHead.y + otherVel.y)) ||
							currSnake.checkCollision(new Vector(otherHead.x + otherVel.x * 2, otherHead.y + otherVel.y * 2))) {
                            currSnake.isDead = true;
                        }
			}   }
		}
		return false;
		
	}

}
