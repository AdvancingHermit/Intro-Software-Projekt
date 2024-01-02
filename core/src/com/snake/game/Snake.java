package com.snake.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.snake.game.util.Vector;

public class Snake {

    private ArrayList<Vector> positions;
    private Vector vel;
    private char key;
    private int[] keys;
    private boolean hasEaten;
    public boolean isDead;
    private int counter = 0;

    public Snake(int x, int y) {
        Vector pos = new Vector(x / 2, y / 2);
        positions = new ArrayList<Vector>();
        for (int i = 10; i >= 0; i--) {
            Vector position = new Vector(pos.x, pos.y - i);
            this.positions.add(position);
        }

        int[] keys = { Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D };
        this.keys = keys;
        vel = new Vector(-1, 0);
        key = 'A';

    }

    public Snake(int x, int y, int[] keys) {
        this(x, y);
        this.keys = keys;
    }

    public void move() {

        if (Gdx.input.isKeyPressed(keys[0]) && !vel.equals(new Vector(0, -1))) { // Default W
            key = 'W';
        }
        if (Gdx.input.isKeyPressed(keys[1]) && !vel.equals(new Vector(1, 0))) { // Default A
            key = 'A';
        }
        if (Gdx.input.isKeyPressed(keys[2]) && !vel.equals(new Vector(0, 1))) { // Default S
            key = 'S';
        }
        if (Gdx.input.isKeyPressed(keys[3]) && !vel.equals(new Vector(-1, 0))) { // Default D
            key = 'D';
        }
        if (counter % 30 == 0) {
            
            switch (key) {
                case ('W'):
                    vel = new Vector(0, 1);
                    break;
                case ('A'):
                    vel = new Vector(-1, 0);
                    break;
                case ('S'):
                    vel = new Vector(0, -1);
                    break;
                case ('D'):
                    vel = new Vector(1, 0);
                    break;

            }
            positions.add(positions.get(positions.size() - 1).add(vel));
            if (checkCollision()) {
                isDead = true;
                positions.remove(positions.size()-1);
            }
            if (hasEaten) {
                counter++;
                hasEaten = false;
                return;
            }
            if (!isDead) {
                positions.remove(0);
            }

        }
        counter++;

        

    }

    public boolean checkCollision() {
        for (int i = 0; i < positions.size() - 1; i++) {
            if (positions.get(positions.size() - 1).equals(positions.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCollision(Vector pos) {
        return positions.get(positions.size() - 1).equals(pos);
    }

    public void setHasEaten() {
        this.hasEaten = true;
    }

    public ArrayList<Vector> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Vector> positions) {
        this.positions = positions;
    }

}
