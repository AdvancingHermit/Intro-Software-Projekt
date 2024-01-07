package com.snake.game.handlers;

import com.snake.game.GameFeature;

public class DragonFruitHandler extends GameFeature
{
    int chance;
    int fireTime;



    public DragonFruitHandler (boolean enabled, int chance, int fireTime) {
        super(enabled);
        this.chance = chance;
        this.fireTime = fireTime;
        if (!enabled) chance = 0;
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
