package com.snake.game.util;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class JSON {
    Data data;

    public JSON(Data data) {
        this.data = data;
    }

    public JSON(String path) {
        String info = "";
        try {
            System.out.println(path);
            FileHandle file = Gdx.files.internal(path);
            Scanner fileReader = new Scanner(file.read());
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
            FileHandle file = Gdx.files.local(path + "/data.json");
            if (!file.exists()) {
                System.out.println("File created: " + file.file().getName());
            } else {
                System.out.println("File already exists.");
            }
            file.writeString(toString(), false);

        } catch (GdxRuntimeException e) {
            e.printStackTrace();
        }
    }
}
