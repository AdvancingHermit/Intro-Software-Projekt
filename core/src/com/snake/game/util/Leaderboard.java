package com.snake.game.util;

import java.util.HashMap;

public class Leaderboard{

    HashMap<Integer, Highscore> board; // int er placement mellem 1 - 10, hvor 10 er bedst, og Highscore er data om scoren.
    
    public Leaderboard(HashMap<Integer, Highscore> board){
        this.board = board;
    }

    public Leaderboard(JSON json){
        board = new HashMap<>();
        for(Object placement : json.getData().keySet()){
            board.put(Integer.parseInt(placement.toString()), new Highscore(json.getData().get(placement).toString() ));
        }
    }

    public void updateLeaderboard(Highscore newScore){
        //Går fra værste til bedste score, da en værre score er mere sandsynlig.;
        int i;
        boolean shouldUpdate = false;
        for(i = board.keySet().size(); i > 0; i--){
            if(board.get(i).getScore() < newScore.getScore()){
                shouldUpdate = true;
                break;
            }
        }
        if(shouldUpdate){
            Highscore temp;
            for(i = i; i <= board.keySet().size(); i++){
                temp = newScore;
                newScore = board.get(i);
                board.put(i, temp);
            }
        }
    }

    public String[] forJSON(){
        String[] s = new String[board.size()];

        for(int i = 0; i < board.size(); i++){
            s[i] = (i + 1) + ": " + board.get(i + 1);
        }
        return s;
    }

    
    
}