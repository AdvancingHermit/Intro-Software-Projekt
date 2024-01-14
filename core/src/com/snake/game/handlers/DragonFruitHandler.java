package com.snake.game.handlers;

import com.snake.game.GameFeature;

public class DragonFruitHandler extends GameFeature
{
    int chance;
    int fireTime;



    public DragonFruitHandler (boolean enabled, String featureName, int chance, int fireTime) {
        super(enabled, featureName);
        this.chance = chance;
        this.fireTime = fireTime;
    }
    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
    public int getFireTime() {
        return fireTime;
    }

}
