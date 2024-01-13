package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.snake.game.util.Vector;

//Made by Oliver
//All interactable objects are build from gameobjects
public class GameObject {

    private Vector snakePos;
    private Texture sprite;
    private Vector spritePos;


    public Vector getSpritePos() {
        return spritePos;
    }


    public GameObject(Vector snakePosition, Texture sprite, Vector spritePos){
        this.snakePos = snakePosition;
        this.sprite = sprite;
        this.spritePos = spritePos;
    }

    public Vector getSnakePos() {
        return snakePos;
    }

    public Texture getSprite() {
        return sprite;
    }
}
