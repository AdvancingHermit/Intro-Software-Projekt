package com.snake.game.util;

import java.util.Arrays;
import java.util.Collections;

public class Leaderboard{

    Highscore[] board; // int er placement mellem 1 - 10, hvor 10 er bedst, og Highscore er data om scoren.
    
    public Leaderboard(Highscore[] board){
        this.board = board;
        Arrays.sort(board, Collections.reverseOrder());
    }

    public Leaderboard(JSON json){
        board = new Highscore[json.getData().keySet().size()];
        int i = 1;
        for(Object placement : json.getData().keySet()){
            board[Integer.parseInt(placement.toString()) - 1] = new Highscore(json.getData().get(placement).toString().split(" ")[0], Integer.parseInt(json.getData().get(placement).toString().split(" ")[1]));
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

    
    
}