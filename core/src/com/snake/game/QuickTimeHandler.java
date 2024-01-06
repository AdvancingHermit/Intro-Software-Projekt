package com.snake.game;

public class QuickTimeHandler extends GameFeature{
    private int time;
    public QuickTimeHandler(boolean enabled, int time) {
        super(enabled);
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}