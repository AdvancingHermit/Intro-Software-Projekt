package com.snake.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Button {
    
    private Vector Size; 
    private Vector pos;
    private Texture backArrow;

    public Button(Vector pos, Vector Size) {
        this.Size = Size;
        this.pos = pos;
    }
    public Button(Vector pos, Vector Size, Texture backArrow) {
        this.Size = Size;
        this.pos = pos;
        this.backArrow = backArrow;
        
	}
    public Vector getpos(){
        return pos;
    } 
    public Vector getSize(){
        return Size;
    }
    public Texture getbackArrow(){
        return backArrow;
    }


	public boolean clickedButton() {
		if (Gdx.input.getX() >= pos.x + Size.x / 2 // Creating start minus n hitbox
				&& Gdx.input.getX() <= pos.x + Size.x / 2 + 300
				&& Gdx.input.getY() <= pos.y - Size.y / 2 + 400
				&& Gdx.input.getY() >= pos.y - Size.y / 2 + 300 &&
				(Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT))) {
			return true;
		}
        return false;
	}
}
