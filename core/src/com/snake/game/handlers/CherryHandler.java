package com.snake.game.handlers;

import com.snake.game.GameFeature;

//Made by Oliver
public class CherryHandler extends GameFeature  {
    private int chance;

    public CherryHandler(boolean enabled, String featureName, int chance) {
        super(enabled, featureName);
        this.chance = chance;
    }

    @Override
    public void disable(){
         chance = 0;
         super.disable();
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}
