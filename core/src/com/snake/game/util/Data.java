package com.snake.game.util;

public class Data{
    String username;
    int highScore;
    String date; 

    public Data(String username, int highScore, String date){
        this.username = username;
        this.highScore = highScore;
        this.date = date;
    }

    public String getUserName(){
        return username;
    }
    public int getHighScore(){
        return highScore;
    }
    public String getDate(){
        return date;
    }

}