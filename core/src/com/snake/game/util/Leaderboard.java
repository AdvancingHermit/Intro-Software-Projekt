//Christian

package com.snake.game.util;

import java.util.Arrays;
import java.util.Collections;

public class Leaderboard {

    Highscore[] board; // int er placement mellem 1 - 10, hvor 10 er bedst, og Highscore er data om
                       // scoren.
    int maxScores = 10;

    public Leaderboard(Highscore[] board) {
        this.board = board;
        Arrays.sort(board, Collections.reverseOrder());
    }

    public Leaderboard(JSON json) {
        if(json.getData().get("leaderboard").toString().equals("")){
            board = new Highscore[0];
            return;
        }

        String[] temp = json.getData().get("leaderboard").toString().split("},");
       
        board = new Highscore[temp.length];

        String[] temp2;
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].replace("{", "").replace("}", "");

            temp2 = temp[i].split(",");
            int index1 = temp2[0].toString().indexOf(":");

            int placement = Integer.parseInt(
                    temp2[0].toString().substring(index1 + 1, findWordEndIndex(temp2[0].toString(), index1)).trim());
            String username = temp2[1].toString().substring(temp2[1].toString().indexOf(":") + 1).trim();

            int score = Integer.parseInt(temp2[2].toString().substring(temp2[2].toString().indexOf(":") + 1).trim());

            int features = Integer.parseInt(temp2[3].toString().substring(temp2[3].toString().indexOf(":") + 1).trim());

            board[placement - 1] = new Highscore(username, score, features);
        }
        forJSON();
    }

    public void updateLeaderboard(Highscore newScore) {
        int featureCounter = 0;
        for(int i = 0; i < board.length; i++) {
            if(board[i].getFeatures() == newScore.getFeatures()) {
                featureCounter++;
            }
        }
        if(board.length == 0){
            board = new Highscore[1];
            board[0] = newScore;
            return;
        }


        if (featureCounter >= maxScores) {
            if (newScore.getScore() > board[board.length - 1].getScore() && newScore.getFeatures() == board[board.length - 1].getFeatures()) {
                board[board.length - 1] = newScore;
                Arrays.sort(board, Collections.reverseOrder());
            }
        } else {
            Highscore[] temp = new Highscore[board.length + 1];
            for (int i = 0; i < board.length; i++) {
                temp[i] = board[i];
            }
            temp[temp.length - 1] = newScore;
            board = temp;
            Arrays.sort(board, Collections.reverseOrder());
        }

    }

    public Highscore[] getLeaderboard() {
        return board.clone();
    }

    public String[] forJSON() {
        String[] sArr = new String[1];
        sArr[0] = "leaderboard: [";

        for (int i = 0; i < board.length; i++) {
            sArr[0] += "{";

            sArr[0] += "placement: " + (i + 1) + ", ";
            sArr[0] += "username: " + board[i].getUsername() + ", ";
            sArr[0] += "score: " + board[i].getScore() + ", ";
            sArr[0] += "features: " + board[i].getFeatures() + ", ";

            sArr[0] = sArr[0].substring(0, sArr[0].length() - 2);
            sArr[0] += "}, ";

        }

        sArr[0] = sArr[0].substring(0, sArr[0].length() - 2);
        sArr[0] += "]";

        return sArr;
    }

    private int findWordEndIndex(String s, int index) {
        int i;
        for (i = index + 2; i < s.length(); i++) {
            if (s.charAt(i) == ',') {
                return i;
            }
        }
        return i;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < board.length; i++) {
            s += (i + 1) + ": " + board[i] + "\n";
        }
        return s;
    }

}