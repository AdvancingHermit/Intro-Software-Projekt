package com.snake.game.handlers;

import com.snake.game.GameFeature;

public class CoffeeBeanHandler extends GameFeature {
    private int chance;
    private int coffeeSpeed;
    private int coffeDuration;

    public int getCoffeeSpeed() {
        return coffeeSpeed;
    }

    public int getCoffeDuration() {
        return coffeDuration;
    }

    public CoffeeBeanHandler(boolean enabled, String featureName, int chance, int coffeeSpeed, int coffeeDuration) {
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
