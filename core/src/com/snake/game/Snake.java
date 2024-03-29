package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.snake.game.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class Snake {

    private ArrayList<Vector> positions;

    private final int snakeSize = 1;
    private Vector vel;
    private char key;
    private int[] keys;

    public boolean isDead;
    private int counter = 0;
    private int startMaxCounter  ;
    private int maxcounter;



    private HashMap<Character, Vector> keyVectorMap = new HashMap<>();
    public HashMap<Vector, Character> keyVectorMapReversed = new HashMap<>();

    //Quick Time Event Variables
    private int quickTimeCounter = 0;

    private int speedCounter = 0;


    //Dragon Fruit Variables
    private int fireCounter = 0;
    boolean fireActive = false;
    private Vector dragonOldVel;

    private Vector oldVel;

    private double startTime = System.currentTimeMillis();

    private int highscore;

    private int growth = 0;
    private int score = 0;

    private boolean hasDeadMoved = false;


    private Vector lastRemoved;
    private ParticleEffect effect = new ParticleEffect();


    public Snake(int x, int y, int maxcounter) {
        this.maxcounter = maxcounter;
        startMaxCounter = maxcounter;
        Vector pos = new Vector(x, y);
        positions = new ArrayList<>();
        for (int i = snakeSize; i >= 0; i--) {
            Vector position = new Vector(pos.x, pos.y - i);
            this.positions.add(position);
        }

        int[] keys = { Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D };
        this.keys = keys;
        vel = new Vector(-1, 0);
        key = 'A';
        keyVectorMap.put('W', new Vector(0, 1));
        keyVectorMap.put('A', new Vector(-1, 0));
        keyVectorMap.put('S', new Vector(0, -1));
        keyVectorMap.put('D', new Vector(1, 0));
        keyVectorMapReversed.put(new Vector(0, 1), 'W');
        keyVectorMapReversed.put(new Vector(-1, 0), 'A');
        keyVectorMapReversed.put(new Vector(0, -1), 'S');
        keyVectorMapReversed.put(new Vector(1, 0), 'D');

		effect.load(Gdx.files.internal("particles/fire.particle"), Gdx.files.internal("particles"));
		effect.start();

    }

    public Snake(int x, int y, int maxcounter, int[] keys) {
        this(x, y, maxcounter);
        this.keys = keys;
    }

    //Made by Oliver and Oscar
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
        if (counter % maxcounter == 0) {
            if (speedCounter > 0){
                speedCounter--;
            } else {
                maxcounter = startMaxCounter;
            }

            vel = keyVectorMap.get(key);


            positions.add(positions.get(positions.size() - 1).add(vel));
            if (checkCollision()) {
                isDead = true;
                positions.remove(positions.size() - 1);
            }
            handleNegativeGrowth();

            if (growth > 0) {
                counter++;
                growth--;
                return;
            }
            if (!isDead) {
                lastRemoved = positions.get(0);
                positions.remove(0);
            }

        }
        counter++;
    }

    //Made by Oliver
    private void handleNegativeGrowth() {
        while (growth < 0 && positions.size() > 3){
            growth++;
            positions.remove(0);
        }
        if (growth < 0 && positions.size() == 3){
            growth = 0;
        }
    }

    //Made by Oscar
    public void quickTime(){
        oldVel = vel;
        quickTimeCounter++;
    }
    //Made by Oscar
    public void dragonFruit(){
        dragonOldVel = vel;
        fireCounter++;
    }
    public Vector getDragonOldVel(){
        return dragonOldVel;
    }

    public int getFireCounter() {
        return fireCounter;
    }
    public void setFireCounter(int fireCounter) {
        this.fireCounter = fireCounter;
    }

    public void moveBack(){
        //Christian
        if (hasDeadMoved){
            return;
        }
        positions.add(0, lastRemoved);
        positions.remove(positions.size() - 1);
        hasDeadMoved = true;
    }
    //Made by Oliver with support from Oscar
    public boolean checkCollision() {

        for (int i = 0; i < positions.size() - 1; i++) {
            if (positions.get(positions.size() - 1).equals(positions.get(i))) {
                isDead = true;
                return true;
            }
        }
        return false;
    }

    //Made by Oliver
    public boolean checkCollision(Vector pos) {
        return (positions.get(positions.size() - 1)).equals(pos);
    }
    //Made by Oliver
    public void setHasEaten(Fruit fruit) {
        score += fruit.getScore();
        growth += fruit.getGrowth();
    }

    public ArrayList<Vector> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Vector> positions) {
        this.positions = positions;
    }

    public int getScore() {
        if (isDead){
            return highscore;
        }
        highscore = (int) ((System.currentTimeMillis() - startTime) * 0.001 + score);
        return highscore;
    }
    public Vector getVel() {
        return vel;
    }
    public void setVel(Vector vel) {
        this.vel = vel;
    }


    public void setKey(char key) {
        this.key = key;
    }
    //Made by Oscar
    public ParticleEffect getEffect() {
        effect.getEmitters().first().getAngle().setHigh((int) vel.angle() -10, (int) vel.angle() + 10);
        effect.getEmitters().first().getAngle().setLow((int) vel.angle() -10, (int) vel.angle() + 10);
        return effect;
   }
    public void setSpeedCounter(int speedCounter) {
        this.speedCounter = speedCounter;
    }
    public int getSpeedCounter() {
        return speedCounter;
    }

    public int getQuickTimeCounter() {
        return this.quickTimeCounter;
    }
    public Vector getOldVel() {
        return this.oldVel;
    }
    public void setQuickTimeCounter(int quickTimeCounter) {
        this.quickTimeCounter = quickTimeCounter;
    }
    public void setMaxcounter(int maxcounter) {
        this.maxcounter = maxcounter;
    }
    public int getMaxcounter() {
        return maxcounter;
    }

    public void setLastRemoved(Vector lastRemoved) {
        this.lastRemoved = lastRemoved;
    }


}
