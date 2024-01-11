package com.snake.game.util;

public class Highscore implements Comparable<Highscore> {

    String username;
    int score;
    int features;
    

    public Highscore(String username, int score, int features){
        this.username = username;
        this.score = score;
        this.features = features;
    }    

    public Highscore(String hString){
        username = hString.split(" ")[0];
        score = Integer.parseInt(hString.split(" ")[1]);
    }

    public String toString(){
        return username + " " + score;
    }
    
    public int getScore(){
        return score;
    }
    public String getUsername(){
        return username;
    }
    public int getFeatures(){
        return features;
    }

    @Override
    public int compareTo(Highscore other){
        return score - other.getScore();
    }
}
