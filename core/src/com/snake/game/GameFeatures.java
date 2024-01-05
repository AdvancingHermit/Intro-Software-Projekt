package com.snake.game;

public class GameFeatures {
    private boolean enabled;

    public GameFeatures(boolean enabled){
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }
}
