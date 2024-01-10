package com.snake.game.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    String username;
    int highScore;
    String date;

    public User(String username){
        this.username = username;
        highScore = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date = formatter.format(new Date());
    }

    public User(String username, int highScore){
        this.username = username;
        this.highScore = highScore;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date = formatter.format(new Date());
    }

    public User(String username, int highScore, String date){
        this.username = username;
        this.highScore = highScore;
        this.date = date;
    }

    public User(JSON json){
        username = json.getData().get("username").toString();
        highScore = Integer.parseInt(json.getData().get("highScore").toString());
        date = json.getData().get("date").toString();
    }

    public String[] forJSON(){
        String[] s = new String[3];
        s[0] = "username: " + username;
        s[1] = "highScore: " + highScore;
        s[2] = "date: " + date;
        return s;
    }

    public void saveUser(){
        JSON json = new JSON(forJSON());
        json.createFile("data/data.json");
    }
    
    public String toString(){
        return username + " " + highScore + " " + date;
    }
    public String getUsername(){
        return username;
    }
    public int getHighScore(){
        return highScore;
    }
    public String getDate(){
        return date;
    }
}
