package com.snake.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.snake.game.GameFeature;

public class Button {

    private Vector Size;
    private Vector pos;
    private Texture backArrow;
    private int screenHeight = Gdx.graphics.getHeight();
    private int screenWidth = Gdx.graphics.getWidth();
    private GameFeature handler;
    private String text; 
    private BitmapFont font;

    public Button(Vector pos, Vector Size, BitmapFont font, String text) {
        this.Size = Size;
        this.pos = pos;
        this.text = text;
        this.font = font;
    
    }
      public Button(Vector pos, Vector Size, GameFeature handler) {
        this.Size = Size;
        this.pos = pos;
        this.handler = handler;

    }

    public Button(Vector pos, Vector Size, Texture backArrow) {
        this.Size = Size;
        this.pos = pos;
        this.backArrow = backArrow;

    }
    public GameFeature gethandler(){
        return handler;
    }

    public Vector getpos() {
        return pos;
    }

    public Vector getSize() {
        return Size;
    }

    public Texture getbackArrow() {
        return backArrow;
    }
    public void toggleisEnabled(){
        handler.toggle();
    }
    public boolean getState(){
        return handler.isEnabled();
    }
    public String getfeatureName(){
        return handler.getfeatureName();
    }
    public String gettext(){
        return text;
    }
    public BitmapFont getfont(){
        return font;
    }
    public boolean clickedButton() {
        if (backArrow == null) {
            if (Gdx.input.getX() >= pos.x // Hitbox, if create by shape
                    && Gdx.input.getX() <= pos.x + Size.x
                    && Gdx.input.getY() <= screenHeight - pos.y
                    && Gdx.input.getY() >= screenHeight - pos.y - Size.y) {
                return true;
            }
        } else {
            if (Gdx.input.getX() >= pos.x + screenWidth / 2 // Hitbox, if create by Batch
                    && Gdx.input.getX() <= pos.x + screenWidth / 2 + Size.x
                    && Gdx.input.getY() <= pos.y - screenHeight / 2 + 4 * Size.y
                    && Gdx.input.getY() >= pos.y - screenHeight / 2 + 3 * Size.y) {
                return true;
            }
        }
        return false;
    }
}
