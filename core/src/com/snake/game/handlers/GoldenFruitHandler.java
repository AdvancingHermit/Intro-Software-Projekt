package com.snake.game.handlers;

import com.snake.game.GameFeature;

public class GoldenFruitHandler extends GameFeature
{
    private int chance;


    public GoldenFruitHandler(boolean enabled, int chance) {
        super(enabled);
        this.chance = chance;
        if (!enabled) this.chance = 0;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

}
