package com.snake.game.util;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputBox {

    ArrayList<Character> inputs;
    boolean keyUp;
    char prevKey;
    int counter = 0;
    // Type 0 = strings, 1 = numbers
    int type;

    public InputBox() {
        inputs = new ArrayList<Character>();
        keyUp = true;
    }

    public void update() {
        char pressedKey = getPressedKey();
        if (pressedKey != '\0' && keyUp && isNumber(pressedKey) && type == 1) {
            inputs.add(pressedKey);
            keyUp = false;
        }
        else if (pressedKey != '\0' && keyUp && type == 0) {
            if(!isShiftPressed()){
                inputs.add((pressedKey + "").toLowerCase().charAt(0));
            }
            else{
                inputs.add(pressedKey);
            }
            keyUp = false;
        } else if (pressedKey == '\0') {
            keyUp = true;
        }
    }

    public String getString() {
        String string = "";
        for (int i = 0; i < inputs.size(); i++) {
            string += inputs.get(i);
        }
        return string;
    }

    public int getNumber() {
        if(type != 1){
            return -1;
        }
        String string = "";
        for (int i = 0; i < inputs.size(); i++) {
            string += inputs.get(i);
        }
        return Integer.parseInt(string);
    }

    public boolean isShiftPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT);
    }

    private boolean isNumber(char c) {
        return c == '0' || c == '1' || c == '2' || c == '3' || c == '4'
                || c == '5' || c == '6' || c == '7' || c == '8' || c == '9';
    }

    public char getPressedKey() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            return 'A';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.B)) {
            return 'B';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            return 'C';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            return 'D';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            return 'E';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            return 'F';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            return 'G';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            return 'H';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            return 'I';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            return 'J';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            return 'K';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            return 'L';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            return 'M';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            return 'N';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            return 'O';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            return 'P';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            return 'Q';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            return 'R';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            return 'S';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            return 'T';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.U)) {
            return 'U';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.V)) {
            return 'V';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            return 'W';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            return 'X';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
            return 'Y';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            return 'Z';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_0)) {
            return '0';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            return '1';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            return '2';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            return '3';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            return '4';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            return '5';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            return '6';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_7)) {
            return '7';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_8)) {
            return '8';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_9)) {
            return '9';
        }
        return '\0'; // Return a null character if no key is pressed
    }
}