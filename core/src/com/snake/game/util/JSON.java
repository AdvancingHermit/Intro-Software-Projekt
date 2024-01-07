package com.snake.game.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class JSON {
    Data data;

    public JSON(Data data) {
        this.data = data;
    }

    public JSON(String path) {
        String info = "";
        try {
            File file = new File(path);
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                info = fileReader.nextLine();
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] splitter = info.substring(1, info.length() - 1).split(",");
        String username = splitter[0].split(":")[1].trim().replace("\"", "");
        int highScore = Integer.parseInt(splitter[1].split(":")[1].trim());
        String date = splitter[2].split(":")[1].trim().replace("\"", "");
        this.data = new Data(username, highScore, date);
    }

    public String getJSON() {
        String json = "{\n" +
                "  \"username\": \"" + data.username + "\",\n" +
                "  \"highScore\": " + data.highScore + ",\n" +
                "  \"date\": \"" + data.date + "\"\n" +
                "}";
        return json;
    }

    public String toString(){
        return "{\"username\": \"" + data.username +
                "\", \"highScore\": " + data.highScore +
                ", \"date\": \"" + data.date + "\"}";
    }

    public Data getData() {
        return data;
    }

    public void createFile(String path) {
        try {
            File file = new File(path + "/data.json");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter writer = new FileWriter(path + "/data.json");
            writer.write(toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
