package com.snake.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class InputBox {

    ArrayList<Character> inputs;
    boolean keyUp;
    char prevKey;
    int counter = 0;
    // Type 0 = strings, 1 = numbers
    int type;
    boolean isEnabled;
    Vector position;
    Vector size;
    FreeTypeFontGenerator generator;
    FreeTypeFontParameter parameter;
    BitmapFont font;
    GlyphLayout text;
    int textWidth;
    boolean mouseUp = true;

    public InputBox(int type, Vector position, Vector size) {
        inputs = new ArrayList<Character>();
        text = new GlyphLayout();
        keyUp = true;
        this.type = type;
        this.position = position;
        this.size = size;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Retroville NC.ttf"));
        parameter = new FreeTypeFontParameter();
    }

    public Object[] getFont() {
        Object[] sender = new Object[2];
        parameter.size = size.y;
        parameter.size = getSize().y;
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK);
        text.setText(font, getString());

        sender[0] = font;
        sender[1] = text;

        return sender;
    }

    public void enable(int screenHeight) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (mouseUp) {
                mouseUp = false;
                int y = screenHeight - Gdx.input.getY();
                if (Gdx.input.getX() > position.x && Gdx.input.getX() < position.x + size.x && y > position.y
                        && y < position.y + size.y) {
                    isEnabled = !isEnabled;
                } else {
                    isEnabled = false;
                }
            }
        } else if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            mouseUp = true;
        }
    }

    public void update() {
        char pressedKey = getPressedKey();
        if (isEnabled) {
            if (pressedKey != '\0' && pressedKey != '\b' && keyUp && isNumber(pressedKey) && type == 1) {
                inputs.add(pressedKey);
                keyUp = false;
            } else if (pressedKey != '\0' && pressedKey != '\b' && keyUp && type == 0) {
                if (!isShiftPressed()) {
                    inputs.add((pressedKey + "").toLowerCase().charAt(0));
                } else {
                    inputs.add(pressedKey);
                }
                keyUp = false;
            } else if (pressedKey == '\b' && keyUp) {
                if (inputs.size() > 0) {
                    inputs.remove(inputs.size() - 1);
                }
                keyUp = false;
            } else if (pressedKey == '\0') {
                keyUp = true;
            }
        }
    }

    public Rectangle[] show() {
        Rectangle rect = new Rectangle(position.x, position.y, size.x, size.y);
        Rectangle curserBlink = new Rectangle(position.x + 2 + textWidth, position.y + 2, 0, 0);
        
        if (isEnabled) {
            textWidth = (int) text.width;
            if (counter % 60 < 30) {
                curserBlink = new Rectangle(position.x + 2 + textWidth, position.y + 2, 4, size.y - 4);
            }
            counter++;
        }
        return new Rectangle[] { rect, curserBlink };
    }

    public String getString() {
        String string = "";
        for (int i = 0; i < inputs.size(); i++) {
            string += inputs.get(i);
        }
        return string;
    }

    public int getNumber() {
        if (type != 1) {
            return -1;
        }
        String string = "";
        for (int i = 0; i < inputs.size(); i++) {
            string += inputs.get(i);
        }
        return Integer.parseInt(string);
    }

    private boolean isShiftPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT);
    }

    private boolean isNumber(char c) {
        String regex = "^[0-9]*$";
        return ("" + c).matches(regex);
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getSize() {
        return size;
    }

    private char getPressedKey() {
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
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            return '\b';
        }
        return '\0'; // Return a null character if no key is pressed
    }
}