package com.snake.game;

public class GameFeature {
    private boolean enabled;

    public GameFeature(boolean enabled){
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
    public void toggle() {
        this.enabled = !this.enabled;
    }
}
