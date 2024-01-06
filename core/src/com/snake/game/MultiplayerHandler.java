package com.snake.game;

public class MultiplayerHandler extends GameFeatures{
    
    private int playerAmount;
    private final int maxPlayerAmount = 3;

    public MultiplayerHandler(boolean enabled, int playerAmount) {
        super(enabled);
        this.playerAmount = Math.min(Math.max(playerAmount, 2), maxPlayerAmount);

    }

    public int getPlayerAmount() {
        return playerAmount;
    }
}
