package com.snake.game;

public class GoldenFruitHandler extends GameFeatures
{
    int chance;



    public GoldenFruitHandler(boolean enabled, int chance) {
        super(enabled);
        this.chance = chance;
    }
    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

}
