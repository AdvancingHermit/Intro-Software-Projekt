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
    private final int startMaxCounter = 5;
    private int maxcounter = startMaxCounter;



    private HashMap<Character, Vector> keyVectorMap = new HashMap<Character, Vector>();
    public HashMap<Vector, Character> keyVectorMapReversed = new HashMap<Vector, Character>();

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

    private int grow = 0;
    private int score = 0;

    private boolean hasDeadMoved = false;


    private Vector lastRemoved;
    private ParticleEffect effect = new ParticleEffect();


    public Snake(int x, int y) {
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

		effect.load(Gdx.files.internal("particles/fire.p"), Gdx.files.internal("particles"));
		effect.start();

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

            if (grow > 0) {
                counter++;
                grow--;
                return;
            }
            if (!isDead) {
                lastRemoved = positions.get(0);
                positions.remove(0);
            }

        }
        counter++;
    }

    private void handleNegativeGrowth() {
        while (grow < 0 && positions.size() > 3){
            grow++;
            positions.remove(0);
        }
        if (grow < 0 && positions.size() == 3){
            grow = 0;
        }
    }

    public void quickTime(){
        oldVel = vel;
        quickTimeCounter++;
    }

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
        if (hasDeadMoved){
            return;
        }
        positions.add(0, lastRemoved);
        positions.remove(positions.size() - 1);
        hasDeadMoved = true;
    }

    public boolean checkCollision() {

        for (int i = 0; i < positions.size() - 1; i++) {
            if (positions.get(positions.size() - 1).equals(positions.get(i))) {
                isDead = true;
                return true;
            }
        }
        return false;
    }

    public boolean checkCollision(Vector pos) {
        return (positions.get(positions.size() - 1)).equals(pos);
    }

    public void setHasEaten(Fruit fruit) {
        score += fruit.getScore();
        grow += fruit.getGrowth();
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

    public void setLastRemoved(Vector lastRemoved) {
        this.lastRemoved = lastRemoved;
    }


}
