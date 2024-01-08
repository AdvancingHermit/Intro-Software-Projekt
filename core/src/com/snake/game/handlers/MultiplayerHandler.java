package com.snake.game.handlers;

import com.snake.game.GameFeature;

public class MultiplayerHandler extends GameFeature{
    
    private int playerAmount;
    private final int maxPlayerAmount = 3;

    public MultiplayerHandler(boolean enabled,String featureName, int chance) {
        super(enabled, featureName);
        this.playerAmount = Math.min(Math.max(playerAmount, 2), maxPlayerAmount);

    }

    public int getPlayerAmount() {
        return playerAmount;
    }
}
