package com.snake.game.util;

public class Users {

    User[] users;
    int numUsers = 0;

    public Users(User[] users) {
        this.users = users;
    }

    public Users(JSON json) {
        users = new User[json.getData().get("users").toString().replace("{", "").split("}").length];
        int i = 0;
        String uName = "";
        int uScore = 0;
        String uDate = "";
        for (Object user : json.getData().get("users").toString().replace("{", "").split("}")) {
            String[] valueArr = user.toString().trim().split(",");

            for (int j = 0; j < valueArr.length; j++) {
                if (valueArr[j].contains("username")) {
                    uName = valueArr[j].trim().split(":")[1].trim();
                } else if (valueArr[j].contains("highScore")) {
                    uScore = Integer.parseInt(valueArr[j].trim().split(":")[1].trim());
                } else if (valueArr[j].contains("date")) {
                    uDate = valueArr[j].trim().split(":")[1].trim();
                }
            }
            users[i] = new User(uName, uScore, uDate);
            i++;
        }
    }

    public void addUser(User newUser) {
        if (newUser.getUsername().equals("")) {
            return;
        }
        User[] temp = new User[users.length + 1];
        for (int i = 0; i < users.length; i++) {
            temp[i] = users[i];
        }
        temp[temp.length - 1] = newUser;
        users = temp;
    }

    public void updateUser(User newUser) {
        boolean isUser = false;
        for (int i = 0; i < users.length; i++) {
            if (users[i].getUsername().equals(newUser.getUsername())) {
                users[i] = newUser;
                isUser = true;
            }
        }
        if (!isUser) {
            addUser(newUser);
        }
    }

    public User[] getUsers() {
        return users.clone();
    }

    public String[] forJSON() {
        String[] sArr = new String[1];
        sArr[0] = "users: [";

        for (int i = 0; i < users.length; i++) {
            sArr[0] += "{";
            for (int j = 0; j < users[i].forJSON().length; j++) {
                sArr[0] += users[i].forJSON()[j] + ", ";
            }
            sArr[0] = sArr[0].substring(0, sArr[0].length() - 2);
            sArr[0] += "}, ";
        }

        sArr[0] = sArr[0].substring(0, sArr[0].length() - 2);
        sArr[0] += "]";

        return sArr;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < users.length; i++) {
            s += users[i].toString() + "\n";
        }
        return s;
    }

}
