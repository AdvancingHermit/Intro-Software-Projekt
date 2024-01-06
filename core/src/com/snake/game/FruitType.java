package com.snake.game;

import com.badlogic.gdx.graphics.Texture;

public class FruitType{

    private Texture sprite;
    private int score;
    private int growth;
    public FruitType(Texture sprite, int score, int growth) {
        this.sprite = sprite;
        this.score = score;
        this.growth = growth;
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


}
