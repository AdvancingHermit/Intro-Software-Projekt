package com.snake.game;

import com.badlogic.gdx.graphics.Texture;

//Made by Oliver
//Fruitype, defines the main attributes of a fruit, that they all share.
public class FruitType{

    private Texture sprite;
    private int score;
    private int growth;
    private int chance;
    GameFeature handler;


    public FruitType(Texture sprite, int score, int growth, int chance, GameFeature handler) {
        this.sprite = sprite;
        this.score = score;
        this.growth = growth;
        this.chance = chance;
        this.handler = handler;
    }

    public Texture getSprite() {
        return sprite;
    }

    public int getScore() {
        return score;
    }

    public int getGrowth() {
        return growth;
    }


    public int getChance() {
        return chance;
    }
    public void setChance(int chance) {
        this.chance = chance;
    }

    public boolean isEnabled() {
        return handler.isEnabled();
    }
}
