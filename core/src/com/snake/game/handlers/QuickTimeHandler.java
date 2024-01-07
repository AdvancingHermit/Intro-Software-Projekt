package com.snake.game.handlers;

import com.snake.game.GameFeature;

public class QuickTimeHandler extends GameFeature{
    private int time;
    public QuickTimeHandler(boolean enabled,String featureName, int time) {
        super(enabled, featureName);
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}