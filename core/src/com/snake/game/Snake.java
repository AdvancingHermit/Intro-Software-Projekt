package com.snake.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.snake.game.util.Vector;

public class Snake {

    private ArrayList<Vector> positions;
    private Vector vel;
    private int[] keys;
    private boolean hasEaten;


    public Snake(int x, int y) {
        Vector pos = new Vector(x / 2, y / 2);
        positions = new ArrayList<Vector>();
        for (int i = 4; i >= 0; i--) {
            Vector position = new Vector(pos.x, pos.y-i);
            this.positions.add(position);
        }

        int[] keys = {Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D};
        this.keys = keys;
        vel = new Vector(-1, 0);

    }
     public Snake(int x, int y, int[] keys) {
        this(x, y);
        this.keys = keys;
     }


    public void move() {
        if (Gdx.input.isKeyPressed(keys[0]) && !vel.equals(new Vector(0, 1)) ) { //Default W
            vel = new Vector(0, -1);
        }
        if (Gdx.input.isKeyPressed(keys[1]) && !vel.equals( new Vector(1, 0))) { //Default A
            vel = new Vector(-1, 0);
        }
        if (Gdx.input.isKeyPressed(keys[2]) && !vel.equals(new Vector(0, -1))) { //Default S
            vel = new Vector(0, 1);
        }
        if (Gdx.input.isKeyPressed(keys[3]) && !vel.equals(new Vector(-1, 0))) { //Default D
            vel = new Vector(1, 0);
        }
        positions.add(positions.get(positions.size()-1).add(vel));
        if (hasEaten) {
            hasEaten = false;
            return;
        }
        positions.remove(0);
        
    }

    public void setHasEaten() {
        this.hasEaten = true;
    }
    public ArrayList<Vector> getPositions(){
        return positions;
    }
    

}
