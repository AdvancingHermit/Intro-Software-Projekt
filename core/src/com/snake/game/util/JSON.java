//Christian

package com.snake.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Scanner;
import java.util.HashMap;

public class JSON {

    private HashMap<Object, Object> data;

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
     * leaderboard: {
     * "placement": "76",
     * "username": "ss",
     * "score": "0",
     * "features": "-2052069131"
     * }
     */
    public JSON(String path) {

        String info = "";
        try {
            FileHandle file = Gdx.files.internal(path);
            Scanner fileReader = new Scanner(file.read());
            while (fileReader.hasNextLine()) {
                info += fileReader.nextLine().trim();
            }
            fileReader.close();
        } catch (Exception e) {
            if (e instanceof GdxRuntimeException) {
                //Laver en tomt JSON object og fylder ind med tomt user og leaderboard object.
                String[] temp = new String[2];
                temp[0] = "users: []";
                temp[1] = "leaderboard: []";
                data = dataFromString(temp);
                return;
            }
            else {
                e.printStackTrace();
            }
        }

        String[] iwannasee;
        String[] temp1 = new String[0];

        //Forsøger at splitte dataen op i et array af array af objecter.
        temp1 = info.substring(1, info.length() - 1).split("],");
        

        //Hvis der kun er et object, splitter den det op i et array af objecter.
        for (int i = 0; i < temp1.length; i++) {
            if (temp1[i].contains("},\"")) {
                String[] temp2 = temp1[i].split("},\"");
                String[] tempArr = new String[temp1.length + 1];
                for (int j = 0; j < temp1.length; j++) {
                    tempArr[j] = temp1[j];
                }
                tempArr[i] = temp2[0] + "}";
                tempArr[tempArr.length - 1] = temp2[1];
                temp1 = tempArr;
            }
        }
        //Samme men nogle gange er der et mellemrum
        for (int i = 0; i < temp1.length; i++) {
            if (temp1[i].contains("}, \"")) {
                String[] temp2 = temp1[i].split("}, \"");
                String[] tempArr = new String[temp1.length + 1];
                for (int j = 0; j < temp1.length; j++) {
                    tempArr[j] = temp1[j];
                }
                tempArr[i] = temp2[0] + "}";
                tempArr[tempArr.length - 1] = temp2[1];
                temp1 = tempArr;
            }
        }

        iwannasee = new String[temp1.length];

        //Tilføjer mistede tegn til .split metoden.
        for (int i = 0; i < temp1.length; i++) {
            iwannasee[i] = temp1[i].replace("\"", "").trim();
            if (iwannasee[i].contains("[")) {
                iwannasee[i] += "]";
            }
        }
        data = dataFromString(iwannasee);
    }

    public JSON(String[] stringData) {
        data = dataFromString(stringData);
        rightOrder();
    }

    public void addStringData(String[] stringData) {
        data.putAll(dataFromString(stringData));
    }

    // Expects specific type of String[] in format, name: namesValue, name2:
    // name2sValue, osv.
    public HashMap<Object, Object> dataFromString(String[] stringData) {
        HashMap<Object, Object> getData = new HashMap<Object, Object>();

        //Splitter dataen op i et hashmap, i det her tilfælde med to entries: leaderboard og users.
        for (int i = 0; i < stringData.length; i++) {

            if (stringData[i].contains("[")) {
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
        //Formatterer dataen til JSON format.

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

                if (word1.equals(firstWord)) {
                    s = s.substring(0, s.length() - 2) + "}, ";
                    s += "{";
                }

                if (isArr && index == tempObj.indexOf(":")) {
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

    //Finder starten af et ord.
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

    //Finder slutningen af et ord.
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

    public void addData(Object key, Object value) {
        data.put(key, value);
    }

    public Object[] getDataValues() {
        return data.values().toArray();
    }

    public String getValue(String key) {
        return data.get(key).toString();
    }

    //Laver selve json filen, eller overwriter den.
    public void createFile() {
        try {
            FileHandle file = Gdx.files.local("data/data.json");
            if(toString().equals("")){
                file.writeString("{leaderboard: [], users:[]}", false);
                return;
            }
            file.writeString(toString(), false);

        } catch (GdxRuntimeException e) {
            e.printStackTrace();
        }
    }

    private void rightOrder() {
        //Var nødvendigt før. burde ikke være det mere, men jeg har ikke lyst til at fjerne det.
        HashMap<Object, Object> temp = new HashMap<Object, Object>();
        for (Object i : data.keySet()) {
            temp.put(i, data.get(i));
        }
        data = temp;
    }
}