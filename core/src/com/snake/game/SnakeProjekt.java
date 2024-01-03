package com.snake.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.snake.game.util.Vector;
import com.badlogic.gdx.math.Rectangle;

public class SnakeProjekt extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Grid grid;
	ShapeRenderer shape;
	public static OrthographicCamera camera;
	FitViewport viewport;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		grid = new Grid(20, 1);
		shape = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		viewport = new FitViewport(1920, 1080, camera);

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shape.begin(ShapeType.Filled);

		Rectangle[][] shower = grid.show();
		ArrayList<Vector> positions = grid.snakes[0].getPositions();


		for (int i = 0; i < shower.length; i++) {
			for (int j = 0; j < shower[i].length; j++) {

				shape.setColor(Color.WHITE);
				shape.rect(shower[i][j].x, shower[i][j].y, grid.snakeSize, grid.snakeSize);

			}
		}
		for (int k = 0; k < positions.size(); k++) {
			int cx = positions.get(k).x;
			int cy = positions.get(k).y;

			if(cx == grid.gridSize || cx == -1){
				//Skiftes til i, når vi looper over slanger.
				cx = grid.snakes[0].getPositions().get(k).x = grid.gridSize - Math.abs(cx);
			}
			if(cy == grid.gridSize || cy == -1){
				//Skiftes til i, når vi looper over slanger.
				cy = grid.snakes[0].getPositions().get(k).y = grid.gridSize - Math.abs(cy);
			}

			if (k == positions.size()-1) {
				shape.setColor(Color.BLACK);
			} else {
				shape.setColor(Color.GREEN);
			}
			shape.rect(shower[cx][cy].x, shower[cx][cy].y, grid.snakeSize, grid.snakeSize);
		}
		shape.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
