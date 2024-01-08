package com.snake.game.util;

public class Highscore {

    String username;
    int score;
    

    public Highscore(String username, int score){
        this.username = username;
        this.score = score;
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
}
