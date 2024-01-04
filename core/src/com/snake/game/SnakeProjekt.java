package com.snake.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.lang.String;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.snake.game.util.Vector;
import com.badlogic.gdx.math.Rectangle;

public class SnakeProjekt extends ApplicationAdapter {

	enum Scene {
		Main_Scene, Main_Game
	}

	Scene currentSceen = Scene.Main_Scene;
	final private int gridsize = 18;
	final private int snakeAmount = 1;
	SpriteBatch batch;
	Texture img;
	Grid grid;
	ShapeRenderer shape;
	public static OrthographicCamera camera;
	FitViewport viewport;
	BitmapFont font;
	Texture appleSprite;
	List<Fruit> fruits = new ArrayList<>();
	Random random = new Random();

	@Override
	public void create() {
		batch = new SpriteBatch();
		appleSprite = new Texture((Gdx.files.internal("Apple.png")));
		img = new Texture("badlogic.jpg");
		grid = new Grid(gridsize, snakeAmount, Gdx.graphics.getHeight());
		shape = new ShapeRenderer();
		camera = new OrthographicCamera();
		// camera.setToOrtho(false, 1920, 1080);
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		font = new BitmapFont(); 

		// fruits.add(apple);


	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render() {
		switch (currentSceen) {
			case Main_Scene:
				ScreenUtils.clear(0, 0, 1, 1);
				if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
					Gdx.app.exit();
				}
				camera.update();
				batch.setProjectionMatrix(camera.combined);
				shape.begin(ShapeType.Filled);
				shape.rect(viewport.getScreenWidth() / 2 - 100, viewport.getScreenHeight() / 2 - 100, 200, 200);

				if (Gdx.input.getX() >= viewport.getScreenWidth() / 2 - 100
						&& Gdx.input.getX() <= viewport.getScreenWidth() / 2 + 100
						&& Gdx.input.getY() <= viewport.getScreenHeight() / 2 + 100
						&& Gdx.input.getY() >= viewport.getScreenHeight() / 2 - 100
						&& Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					currentSceen = Scene.Main_Game;
				}
				shape.end();
				break;
			case Main_Game:
				ScreenUtils.clear(0, 0, 1, 1);
				if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
					Gdx.app.exit();
				}
				camera.update();
				batch.setProjectionMatrix(camera.combined);

				shape.begin(ShapeType.Filled);

				Rectangle[][] shower = grid.show(viewport.getScreenWidth(), viewport.getScreenHeight());
				ArrayList<Vector> positions = grid.snakes[0].getPositions();
				batch.begin();
				font.draw(batch, String.valueOf(grid.snakes[0].getScore()), 0, 0.4f * viewport.getScreenHeight());
				batch.end();


				for (Rectangle[] rectangles : shower) {
					for (Rectangle rectangle : rectangles) {

						shape.setColor(Color.WHITE);
						shape.rect(rectangle.x, rectangle.y, grid.squareSize, grid.squareSize);
						if (fruits.isEmpty()) {
							int randx = random.nextInt(0, gridsize);
							int randy = random.nextInt(0, gridsize);
							fruits.add(new Fruit(new Vector((int) (randx), (int) (randy)), appleSprite, new Vector((int) ((rectangle.x - (viewport.getScreenWidth()/2)) + grid.squareSize * randx), (int) ((rectangle.y - (viewport.getScreenHeight()/2)) + grid.squareSize * randy))));
						}
 


					}
				}
				for (int k = 0; k < positions.size(); k++) {
					int cx = positions.get(k).x;
					int cy = positions.get(k).y;

					if (cx == grid.gridSize || cx == -1) {
						// Skiftes til i, når vi looper over slanger.
						cx = grid.snakes[0].getPositions().get(k).x = grid.gridSize - Math.abs(cx);
					}
					if (cy == grid.gridSize || cy == -1) {
						// Skiftes til i, når vi looper over slanger.
						cy = grid.snakes[0].getPositions().get(k).y = grid.gridSize - Math.abs(cy);
					}

					if (k == positions.size() - 1) {
						shape.setColor(Color.BLACK);
					} else {
						shape.setColor(Color.GREEN);
					}
					shape.rect(shower[cx][cy].x, shower[cx][cy].y, grid.squareSize, grid.squareSize);
				}
				shape.end();



				batch.begin();
				for (Fruit fruit : fruits) {
					    batch.draw(fruit.getSprite(), (fruit.getSpritePos().x), (fruit.getSpritePos().y), grid.squareSize, grid.squareSize);
				}

				batch.end();
				Iterator<Fruit> fruitIterator = fruits.iterator();
				while (fruitIterator.hasNext()) {
					Fruit fruit = fruitIterator.next();
					if (grid.snakes[0].checkCollision(fruit.getSnakePos())) {
						System.out.println("Nom nom");
						// grid.snakes[0].setScore(grid.snakes[0].getScore + 1)
						fruits.remove(fruit);
					}
					break;
				}
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		shape.dispose();
		appleSprite.dispose();
	}
}
