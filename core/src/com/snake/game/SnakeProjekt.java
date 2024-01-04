package com.snake.game;

import java.util.ArrayList;
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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
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
	final private int gridsize = 20;
	final private int snakeAmount = 1;
	SpriteBatch batch;
	Texture img;
	Grid grid;
	ShapeRenderer shape;
	public static OrthographicCamera camera;
	FitViewport viewport;
	BitmapFont font;
	BitmapFont font2;
	Texture appleSprite;
	List<Fruit> fruits = new ArrayList<>();
	Random random = new Random();
	GlyphLayout scoreNumText;
	GlyphLayout colonText;
	GlyphLayout scoreText;

	@Override
	public void create() {
		batch = new SpriteBatch();
		appleSprite = new Texture((Gdx.files.internal("Apple.png")));
		img = new Texture("badlogic.jpg");
		shape = new ShapeRenderer();
		grid = new Grid(gridsize, snakeAmount,  Gdx.graphics.getHeight() );
		camera = new OrthographicCamera();
		// camera.setToOrtho(false, 1920, 1080);
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Retroville NC.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 72;
		font = generator.generateFont(parameter); // font size 12 pixels
		font.setColor(Color.ORANGE);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/snes.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 90;
		font2 = generator.generateFont(parameter); // font size 12 pixels
		font2.setColor(Color.ORANGE);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!


		scoreText = new GlyphLayout();
		scoreText.setText(font2, "SCORE");
		colonText = new GlyphLayout();
		colonText.setText(font, " : ");
		scoreNumText = new GlyphLayout();
		

		Fruit apple = new Fruit(new Vector(100, 100), appleSprite);
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

				scoreNumText.setText(font2, "22");
				float offset = (scoreNumText.width + scoreText.width + colonText.width) / 2;
				scoreNumText.setText(font2, "" + grid.snakes[0].getScore());

				font2.draw(batch, scoreText, - offset, 0.41f * viewport.getScreenHeight());
				font.draw(batch, colonText, - offset + scoreText.width, 0.41f * viewport.getScreenHeight());
				font2.draw(batch, scoreNumText, - offset + scoreText.width + colonText.width, 0.41f * viewport.getScreenHeight());

				batch.end();
				for (Rectangle[] rectangles : shower) {
					for (Rectangle rectangle : rectangles) {

						shape.setColor(Color.WHITE);
						shape.rect(rectangle.x, rectangle.y, grid.squareSize, grid.squareSize);

					}
				}
				for (int k = 0; k < positions.size(); k++) {
					int cx = positions.get(k).x;
					int cy = positions.get(k).y;

					if (cx == grid.gridSize || cx == -1) {
						// Skiftes til i, når vi looper over slanger.
						cx = grid.snakes[0].getPositions().get(k).x = grid.gridSize - Math.abs(cx);
						grid.snakes[0].checkCollision();
					}
					if (cy == grid.gridSize || cy == -1) {
						// Skiftes til i, når vi looper over slanger.
						cy = grid.snakes[0].getPositions().get(k).y = grid.gridSize - Math.abs(cy);
						grid.snakes[0].checkCollision();
					}

					if (k == positions.size() - 1) {
						shape.setColor(Color.BLACK);
					} else {
						shape.setColor(Color.GREEN);
					}
					shape.rect(shower[cx][cy].x, shower[cx][cy].y, grid.squareSize, grid.squareSize);
				}
				shape.end();

				if (fruits.isEmpty()) {
					int randx = random.nextInt(1, gridsize + 1) * 40;
					int randy = randx + 40;
					for (Vector pos : grid.snakes[0].getPositions()) {
						if (new Vector(randx, randy).equals(pos)) {
							break;
						}
					}
					fruits.add(new Fruit(new Vector(randx, randy), appleSprite));
				}

				batch.begin();
				// batch.draw(appleSprite, 195,135,90f,90f);
				for (Fruit fruit : fruits) {
					batch.draw(fruit.getSprite(), fruit.getPosition().x, fruit.getPosition().y, (float) 90, (float) 90);
				}

				batch.end();
				for (Fruit fruit : fruits) {
					if (grid.snakes[0].checkCollision(fruit.getPosition())) {
						// grid.snakes[0].setScore(grid.snakes[0].getScore + 1)
						fruits.remove(fruit);
					}
				}
				break;
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
