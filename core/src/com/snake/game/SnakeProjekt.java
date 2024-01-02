package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Rectangle;

public class SnakeProjekt extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Grid grid;
	ShapeRenderer shape;
	public static OrthographicCamera camera;
	FitViewport viewport;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		grid = new Grid(1);
		shape = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		viewport = new FitViewport(1920, 1080, camera);
		


	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shape.begin(ShapeType.Filled);

		Rectangle[][] shower = grid.show();


		for(int i = 0; i < shower.length; i++){
			for(int j = 0; j < shower[i].length; j++){
				shape.rect(shower[i][j].x, shower[i][j].y, grid.snakeSize, grid.snakeSize);
			}
		}

		shape.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
