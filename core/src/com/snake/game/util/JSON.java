package com.snake.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Scanner;
import java.util.HashMap;

public class JSON {

    HashMap<Object, Object> data;

    public JSON(HashMap<Object, Object> data) {
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
        //Substring removes these { }
        data = dataFromString(info.substring(1, info.length() - 1).replace("\"", "").split(","));
    }

    public JSON(String[] stringData) {
        data = dataFromString(stringData);
    }

    // Expects specific type of String[] in format, name: namesValue, name2: name2sValue, osv.
    public HashMap<Object, Object> dataFromString(String[] stringData) {
        HashMap<Object, Object> getData = new HashMap<Object, Object>();

        for (int i = 0; i < stringData.length; i++) {
            getData.put(stringData[i].split(":")[0].trim().replace("\"", ""), stringData[i].split(":")[1].trim());
        }
        
        return getData;
    }

    public String toString() {
        String s = "{";
        for (Object i : data.keySet()) {
            s += "\"" + i + "\": \"" + data.get(i).toString() +  "\", ";
        }
        return s.substring(0, s.length() - 2) + "}";
    }

    public HashMap<Object, Object> getData() {
        return data;
    }

    public Object[] getDataValues() {
        return data.values().toArray();
    }

    public String getValue(String key) {
        return data.get(key).toString();
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
