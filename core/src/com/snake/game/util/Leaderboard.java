package com.snake.game.util;

import java.util.Arrays;
import java.util.Collections;

public class Leaderboard {

    Highscore[] board; // int er placement mellem 1 - 10, hvor 10 er bedst, og Highscore er data om
                       // scoren.
    int maxScores = 10;
    int numScores = 0;

    public Leaderboard(Highscore[] board) {
        this.board = board;
        Arrays.sort(board, Collections.reverseOrder());
    }

    public Leaderboard(JSON json) {
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
        if (board.length == maxScores) {
            if (newScore.getScore() > board[board.length - 1].getScore()) {
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

    private int findEmptyIndex(String s, int index) {
        int i;
        for (i = index - 1; i > 0; i--) {
            if (s.charAt(i) == ':' || s.charAt(i) == ',') {
                if (s.substring(i, index).contains(",") || s.substring(i, index).contains("{")) {
                    return i + 1;
                }
                return i;
            }
        }
        if (s.substring(0, index).contains(",") || s.substring(0, index).contains("{")) {
            return i + 1;
        }
        return i;
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
    
    public void setMaxScore(int maxScore){
        this.maxScores = maxScore;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < board.length; i++) {
            s += (i + 1) + ": " + board[i] + "\n";
        }
        return s;
    }

}