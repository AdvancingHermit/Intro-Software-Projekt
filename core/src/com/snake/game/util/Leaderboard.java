package com.snake.game.util;

import java.util.Arrays;
import java.util.Collections;

public class Leaderboard{

    Highscore[] board; // int er placement mellem 1 - 10, hvor 10 er bedst, og Highscore er data om scoren.
    int maxScores = 10;
    int numScores = 0;
    
    public Leaderboard(Highscore[] board){
        this.board = board;
        Arrays.sort(board, Collections.reverseOrder());
    }

    public Leaderboard(JSON json){
        board = new Highscore[json.getData().get("leaderboard").toString().replace("{", "").replace("}", "").split(",").length];
        int i = 1;
        for(Object leaderboard : json.getData().get("leaderboard").toString().replace("{", "").replace("}", "").split(",")){
            String[] strArr = leaderboard.toString().split(":");
            String[] hScore = strArr[1].substring(strArr[1].indexOf(":") + 1).trim().split(" ");
            board[Integer.parseInt(strArr[0].trim()) - 1] = new Highscore(hScore[0].trim(), Integer.parseInt(hScore[1].trim()));
        }
        Arrays.sort(board, Collections.reverseOrder());
    }

    public void updateLeaderboard(Highscore newScore){
        if(newScore.getScore() > board[board.length - 1].getScore()){
            board[board.length - 1] = newScore;
            Arrays.sort(board, Collections.reverseOrder());
        }
    }

    public Highscore[] getLeaderboard(){
        return board.clone();
    }

    public String[] forJSON(){
        String[] s = new String[board.length];

        for(int i = 0; i < board.length; i++){
            s[i] = (i + 1) + ": " + board[i];
        }
        return s;
    }

    public String toString(){
        String s = "";
        for(int i = 0; i < board.length; i++){
            s += (i + 1) + ": " + board[i] + "\n";
        }
        return s;
    }

    
    
}