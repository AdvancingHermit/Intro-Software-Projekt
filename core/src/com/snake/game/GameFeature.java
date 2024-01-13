package com.snake.game;


//Made by Oliver
//Gamefeatures/handlers, allows to easily enable and disable extra features in game.
public class GameFeature {
    private boolean enabled;
    private String featureName;

    public GameFeature(boolean enabled, String featureName){
        this.enabled = enabled;
        this.featureName = featureName;
    }

    public boolean isEnabled() {
        return enabled;
    }


    public void disable() {
        this.enabled = false;
    }
    public void toggle() {
        this.enabled = !this.enabled;
    }
    public String getfeatureName(){
        return featureName;
    }
}
