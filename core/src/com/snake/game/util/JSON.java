package com.snake.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Scanner;
import java.util.HashMap;

public class JSON {

    HashMap<Object, Object> data;

    public JSON(HashMap<Object, Object> data) {
        this.data = data;
    }

    // Forventer data i formattet;
    /*
     * users: [
     * {date: 08/01/2024,highScore: 10,username: Test},
     * {date:08/01/2024,highScore: 10,username: Test2},
     * {date: 08/01/2024,highScore: 10,username: Test3}
     * ],
     * leaderboard: {1: Test 100,2: Test2 300,3: Test3 300}
     */
    public JSON(String path) {
        String info = "";
        try {
            System.out.println(path);
            FileHandle file = Gdx.files.internal(path);
            Scanner fileReader = new Scanner(file.read());
            while (fileReader.hasNextLine()) {
                info += fileReader.nextLine().trim();
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] iwannasee = info.substring(1, info.length() - 1).replace("\"", "").split("],");
        for (int i = 0; i < iwannasee.length; i++) {
            if (i < iwannasee.length - 1) {
                iwannasee[i] += "]";
            }
        }
        data = dataFromString(iwannasee);
    }

    public JSON(String[] stringData) {
        data = dataFromString(stringData);
    }

    public void addStringData(String[] stringData) {
        data.putAll(dataFromString(stringData));
    }

    // Expects specific type of String[] in format, name: namesValue, name2:
    // name2sValue, osv.
    public HashMap<Object, Object> dataFromString(String[] stringData) {
        HashMap<Object, Object> getData = new HashMap<Object, Object>();

        for (int i = 0; i < stringData.length; i++) {

            if (stringData[i].contains("[")) {
                System.out.println("HELLO");
                String arrName = stringData[i].split(":")[0].trim();
                String[] s = stringData[i].split(",");
                s[0] = s[0].substring(s[0].indexOf("[") + 1);
                getData.put(arrName,
                        stringData[i].substring(stringData[i].indexOf("[")).replace("[", "").replace("]", "").trim());

            } else {
                getData.put(stringData[i].split(":")[0].trim().replace("\"", ""), stringData[i]
                        .substring(stringData[i].indexOf(":") + 1).replace("{", "").replace("}", "").trim());
            }
        }
        return getData;
    }

    public String toString() {
        String s = "{";
        String firstWord = "";
        for (Object i : data.keySet()) {
            String tempObj = data.get(i).toString();
            int index = tempObj.indexOf(":");
            int index1;
            int index2;
            String temp = "";
            s += "\"" + i.toString() + "\": ";

            boolean isArr = false;
            if (tempObj.contains("},")) {
                isArr = true;
                s += "[";
            }
            s += "{";
            
            while (index >= 0) {
                index1 = findEmptyIndex(tempObj, index);
                index2 = findWordEndIndex(tempObj, index);

                String word1 = tempObj.substring(index1, index).trim();
                String word2 = tempObj.substring(index + 1, index2).trim();

                if(word1.equals(firstWord)){
                    s = s.substring(0, s.length() - 2) + "}, ";
                    s += "{";
                }

                if(isArr && index == tempObj.indexOf(":")){
                    firstWord = tempObj.substring(index1, index).trim();
                }

                s += "\"" + word1 + "\": ";
                s += "\"" + word2 + "\", ";
                index = tempObj.indexOf(":", index + 1);
            }
            if (isArr) {
                s = s.substring(0, s.length() - 2) + "}], ";
            } else {
                s = s.substring(0, s.length() - 2) + "}, ";
            }
        }
        return s.substring(0, s.length() - 2) + "}";
    }

    private int findEmptyIndex(String s, int index) {
        int i;
        for (i = index - 1; i > 0; i--) {
            if (s.charAt(i) == ' ' || s.charAt(i) == ':' || s.charAt(i) == ',' || s.charAt(i) == '{') {
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
            if (s.charAt(i) == ',' || s.charAt(i) == '}' || s.charAt(i) == ':') {
                return i;
            }
        }
        return i;
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

    public void createFile(String path, String fileName) {
        try {
            FileHandle file = Gdx.files.local(path + fileName);
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
