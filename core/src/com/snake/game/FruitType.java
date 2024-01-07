package com.snake.game;

import com.badlogic.gdx.graphics.Texture;

public class FruitType{

    private Texture sprite;
    private int score;
    private int growth;
    private int chance;


    public FruitType(Texture sprite, int score, int growth, int chance) {
        this.sprite = sprite;
        this.score = score;
        this.growth = growth;
        this.chance = chance;
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

}
