package com.snake.game.handlers;

import com.snake.game.GameFeature;

public class CoffeeBeanHandler extends GameFeature {
    private int chance;


    public CoffeeBeanHandler(boolean enabled, String featureName, int chance) {
        super(enabled, featureName);
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
