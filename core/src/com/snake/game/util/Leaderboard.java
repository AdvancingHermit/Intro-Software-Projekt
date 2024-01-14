//Christian

package com.snake.game.util;

import java.util.Arrays;
import java.util.Collections;

public class Leaderboard {

    private Highscore[] board; // int er placement mellem 1 - 10, hvor 10 er bedst, og Highscore er data om scoren.
    private int maxScores = 10;

    public Leaderboard(Highscore[] board) {
        this.board = board;
        Arrays.sort(board, Collections.reverseOrder());
    }

    public Leaderboard(JSON json) {
        //Hvis der ikke er noget leaderboard, skal den ikke prøve at stringparse ikke eksisterende data.
        if (json.getData().get("leaderboard").toString().equals("")) {
            board = new Highscore[0];
            return;
        }

        //Splitter leaderboard arrayet op i et array af highscores.
        String[] temp = json.getData().get("leaderboard").toString().split("},");

        board = new Highscore[temp.length];

        String[] temp2;

        //Looper over de enkelte highscores, og splitter dem op i deres respektive data.
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].replace("{", "").replace("}", "");

            temp2 = temp[i].split(",");
            int index1 = temp2[0].toString().indexOf(":");

            int placement = Integer.parseInt(
                    temp2[0].toString().substring(index1 + 1, findWordEndIndex(temp2[0].toString(), index1)).trim());
            String username = temp2[1].toString().substring(temp2[1].toString().indexOf(":") + 1).trim();

            int score = Integer.parseInt(temp2[2].toString().substring(temp2[2].toString().indexOf(":") + 1).trim());

            int features = Integer.parseInt(temp2[3].toString().substring(temp2[3].toString().indexOf(":") + 1).trim());

            //Tilføjer highscoren til leaderboardet.
            board[placement - 1] = new Highscore(username, score, features);
        }
        Arrays.sort(board, Collections.reverseOrder());
    }

    public void updateLeaderboard(Highscore newScore) {
        //Finder ud af hvor mange andre highscores der har samme features som den nye score.
        int featureCounter = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i].getFeatures() == newScore.getFeatures()) {
                featureCounter++;
            }
        }

        //Hvis der er flere end 10 highscores med samme features, skal den først sammenlignes med den værste score for at se om den kan tilføjes til board.
        if (featureCounter >= maxScores) {
            for (int i = board.length - 1; i >= 0; i--) {
                if (board[i].getFeatures() == newScore.getFeatures()) {
                    if (newScore.getScore() > board[i].getScore()) {
                        board[i] = newScore;
                        Arrays.sort(board, Collections.reverseOrder());
                    }
                    return;
                }
            }
        } 
        //Hvis der er mindre end 10 highscores med samme features, skal den bare tilføjes til board.
        else {
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
        if(board.length == 0){
            updateLeaderboard(new Highscore("anon", 0, 0));
        }

        //Laver et string array over leaderboardet, som kan bruges til at updatere et JSON objekt.

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
        //Finder slutningen af et ord.
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