package com.snake.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.snake.game.util.InputBox;
import com.snake.game.util.Vector;
import com.badlogic.gdx.math.Rectangle;

public class SnakeProjekt extends ApplicationAdapter {

	enum Scene {
		Main_Scene, Main_Game, Main_Setting, Main_Enable_Features
	}

	Scene currentSceen = Scene.Main_Scene;

	private int n = 6;
	private int m = 6;

	private Vector gridsize;

	SpriteBatch batch;
	Texture img;
	Grid grid;
	ShapeRenderer shape;
	public static OrthographicCamera camera;
	FitViewport viewport;
	BitmapFont font;
	BitmapFont font2;

	Texture backArrow;
	BitmapFont font3;

	Texture appleSprite;
	Texture goldenAppleSprite;
	Texture wallSprite;
	Texture snakeBodySprite;
	Texture snakeHeadSprite;
	Texture snakeHeadSidewaysSprite;

	List<Fruit> fruits = new ArrayList<>();
	Random random = new Random();
	GlyphLayout scoreNumText;
	GlyphLayout colonText;
	GlyphLayout scoreText;
	int screenHeight;
	int screenWidth;
	int startButtonX;
	int startButtonY;
	boolean mousePressed;
	int frameCounter = 0;
	InputBox inputBox;
	int backButtonHeight, backButtonWidth, backButtonX, backButtonY;

	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;


	WallHandler wallHandler = new WallHandler(true);
	MultiplayerHandler multiplayerHandler = new MultiplayerHandler(false);
	GoldenFruitHandler goldenFruitHandler = new GoldenFruitHandler(true, 50);

	int fruitAmount = 3;

	@Override
	public void create() {

		batch = new SpriteBatch();
		backArrow = new Texture(Gdx.files.internal("Arrow.png"));
		appleSprite = new Texture((Gdx.files.internal("Apple.png")));
		goldenAppleSprite = new Texture((Gdx.files.internal("GoldenApple.png")));
		wallSprite = new Texture((Gdx.files.internal("wall.jpg")));
		snakeBodySprite = new Texture((Gdx.files.internal("snakebody.png")));
		snakeHeadSprite = new Texture((Gdx.files.internal("snakehead.png")));
		snakeHeadSidewaysSprite = new Texture((Gdx.files.internal("snakeheadsideways.png")));

		img = new Texture("badlogic.jpg");
		shape = new ShapeRenderer();

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		// camera.setToOrtho(false, 1920, 1080);
		viewport = new FitViewport(screenWidth, screenHeight, camera);

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Retroville NC.ttf"));
		parameter = new FreeTypeFontParameter();
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
		scoreText.setText(font2, "Player 1 SCORE");
		colonText = new GlyphLayout();
		colonText.setText(font, " : ");
		scoreNumText = new GlyphLayout();

		inputBox = new InputBox(0);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Retroville NC.ttf"));

		backButtonX = -screenWidth / 2 + 150;
		backButtonY = screenHeight / 2 - 200;
		backButtonWidth = 300;
		backButtonHeight = 100;




	}

	public void drawBackButton() {
		batch.begin();
		batch.draw(backArrow, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
		batch.end();
	}

	public void clickedBackButton() {
		if (Gdx.input.getX() >= backButtonX + screenWidth / 2 // Creating start minus n hitbox
				&& Gdx.input.getX() <= backButtonX + screenWidth / 2 + 300
				&& Gdx.input.getY() <= backButtonY - screenHeight / 2 + 400
				&& Gdx.input.getY() >= backButtonY - screenHeight / 2 + 300 &&
				(Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT))) {
			currentSceen = Scene.Main_Scene;
		}
	}
	public void checkForESC(){
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
					Gdx.app.exit();
				}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render() {
		checkForESC();
		switch (currentSceen) {
			case Main_Scene:
				ScreenUtils.clear(0, 0, 1, 1);
				startButtonX = screenWidth / 2 - 100;
				startButtonY = screenHeight / 2 - 100;
				frameCounter++;
				mousePressed = false;
				camera.update();
				batch.setProjectionMatrix(camera.combined);
				shape.begin(ShapeType.Filled);
				shape.setColor(Color.WHITE);
				shape.rect(startButtonX, startButtonY, 200, 200); // creating start game
				drawBackButton();
				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
					clickedBackButton();
					mousePressed = true;
				}
				if (Gdx.input.getX() >= startButtonX // Creating start game button hitbox
						&& Gdx.input.getX() <= startButtonX + 200
						&& Gdx.input.getY() <= startButtonY + 200
						&& Gdx.input.getY() >= startButtonY
						&& (Gdx.input.isButtonPressed(Input.Buttons.LEFT)
								|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT))) {

					gridsize = new Vector(n, m);
					grid = new Grid(gridsize, multiplayerHandler.isEnabled(), screenHeight);
					if (wallHandler.isEnabled()) {
						grid.walls = grid.wallGenerator(gridsize);
					}
					currentSceen = Scene.Main_Game;
				}
				shape.end();
				break;
			case Main_Setting:
				break;
			case Main_Enable_Features:
				ScreenUtils.clear(0, 0, 1, 1);
				camera.update();
				batch.setProjectionMatrix(camera.combined);
				shape.begin(ShapeType.Filled);
				shape.setColor(Color.RED);
				shape.rect(startButtonX + 400, startButtonY, 100, 200); // creating minus n
				shape.setColor(Color.GREEN);
				shape.rect(startButtonX + 500, startButtonY, 100, 200); // creating plus n
				shape.setColor(Color.RED);
				shape.rect(startButtonX - 400, startButtonY, 100, 200); // creating minus m
				shape.setColor(Color.GREEN);
				shape.rect(startButtonX - 300, startButtonY, 100, 200); // creating plus m
				drawBackButton();
				clickedBackButton();
				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
					mousePressed = true;
				}
				if (frameCounter % 3 == 0) {
					if (Gdx.input.getX() >= startButtonX + 400 // Creating start minus n hitbox
							&& Gdx.input.getX() <= startButtonX + 500
							&& Gdx.input.getY() <= startButtonY + 200
							&& Gdx.input.getY() >= startButtonY
							&& mousePressed) {
						n = Math.max(n - 1, 5);
						mousePressed = false;
					} else if (Gdx.input.getX() >= startButtonX + 500 // Creating start plus n hitbox
							&& Gdx.input.getX() <= startButtonX + 600
							&& Gdx.input.getY() <= startButtonY + 200
							&& Gdx.input.getY() >= startButtonY
							&& mousePressed) {
						n = Math.min(n + 1, 100);
						mousePressed = false;
					}
					if (Gdx.input.getX() >= startButtonX - 400// Creating start minus m hitbox
							&& Gdx.input.getX() <= startButtonX - 300
							&& Gdx.input.getY() <= startButtonY + 200
							&& Gdx.input.getY() >= startButtonY
							&& mousePressed) {
						m = Math.max(m - 1, 5);
						mousePressed = false;
					} else if (Gdx.input.getX() >= startButtonX - 300 // Creating start plus n hitbox
							&& Gdx.input.getX() <= startButtonX - 200
							&& Gdx.input.getY() <= startButtonY + 200
							&& Gdx.input.getY() >= startButtonY
							&& mousePressed) {
						m = Math.min(m + 1, 100);
						mousePressed = false;
					}
				}
				shape.end();
				break;
			case Main_Game:
				ScreenUtils.clear(0, 0, 1, 1);
				camera.update();
				batch.setProjectionMatrix(camera.combined);

				shape.begin(ShapeType.Filled);
				Rectangle[][] shower = grid.show(viewport.getScreenWidth(), viewport.getScreenHeight());

				shape.end();
				batch.begin();
				for (int i = 0; i < grid.snakes.length; i++) {

					scoreNumText.setText(font2, "22");
					scoreText.setText(font2, "PLAYER " + (i + 1) + " SCORE");
					float offset = -(grid.gridSize.x * grid.squareSize) / 2 - 20;
					scoreNumText.setText(font2, "" + grid.snakes[i].getScore());
					font2.draw(batch, scoreText, -offset, (0.41f + 0.075f * -i) * viewport.getScreenHeight());
					font.draw(batch, colonText, -offset + scoreText.width,
							(float) (0.41f + 0.075f * -i) * viewport.getScreenHeight());
					font2.draw(batch, scoreNumText, -offset + scoreText.width + colonText.width - 20,
							(0.41f + 0.075f * -i) * viewport.getScreenHeight());

				}
				batch.end();

				shape.begin(ShapeType.Filled);
				for (Rectangle[] rectangles : shower) {
					for (Rectangle rectangle : rectangles) {
						shape.setColor(Color.WHITE);
						shape.rect(rectangle.x, rectangle.y, grid.squareSize, grid.squareSize);
					}
				}

				spawnFruit(shower);
				shape.end();

				batch.begin();

				snakeUpdater(shower);

				batch.end();

				batch.begin();

				for (Fruit fruit : fruits) {
					batch.draw(fruit.getSprite(), (fruit.getSpritePos().x), (fruit.getSpritePos().y), grid.squareSize,
							grid.squareSize);
				}
				batch.end();
				if (wallHandler.isEnabled()) {
					drawWalls();
				}
				checkFruitCollsions();
				break;
		}

	}

	private void checkFruitCollsions() {
		Iterator<Fruit> fruitIterator = fruits.iterator();
		while (fruitIterator.hasNext()) {
			Fruit fruit = fruitIterator.next();
			System.out.println(fruit.getSnakePos());
			for (Snake snake : grid.snakes) {
				if (snake.checkCollision(fruit.getSnakePos())) {
					snake.setHasEaten(fruit.isGolden());
					fruitIterator.remove();
				}
			}
		}
	}

	private void snakeUpdater(Rectangle[][] shower) {
		for (Snake snake : grid.snakes) {
			ArrayList<Vector> positions = snake.getPositions();
			for (int k = 0; k < positions.size(); k++) {
				int cx = positions.get(k).x;
				int cy = positions.get(k).y;

				if (cx == grid.gridSize.x || cx == -1) {
					// Skiftes til i, når vi looper over slanger.
					cx = positions.get(k).x = grid.gridSize.x - Math.abs(cx);
					snake.move();

				}
				if (cy == grid.gridSize.y || cy == -1) {
					// Skiftes til i, når vi looper over slanger.
					cy = positions.get(k).y = grid.gridSize.y - Math.abs(cy);
					snake.move();
				}

				if (k == positions.size() - 1) {
					shape.setColor(Color.BLACK);
					Sprite sprY = new Sprite(snakeHeadSprite);
					Sprite sprX = new Sprite(snakeHeadSidewaysSprite);
					Vector vel = snake.getVel();
					sprY.setFlip(false, vel.y == 1);
					sprX.setFlip(vel.x == -1, false);
					Sprite spr = vel.x == 0 ? sprY : sprX;
					batch.draw(spr, (int) (shower[cx][cy].x - screenWidth / 2),
							(int) (shower[cx][cy].y - screenHeight / 2), grid.squareSize, grid.squareSize);

				} else {
					batch.draw(snakeBodySprite, (int) (shower[cx][cy].x - screenWidth / 2),
							(int) (shower[cx][cy].y - screenHeight / 2), grid.squareSize, grid.squareSize);
				}
			}
		}
	}

	private void spawnFruit(Rectangle[][] shower) {
		if (fruits.isEmpty()) {
			for (int k = 0; k< fruitAmount;k++) {
				boolean snakeCoversFullScreen = false;
				boolean spawnInSnake = false;
				boolean spawnInFruit = false;
				int snakeSize = 0;
				int randx = random.nextInt(0, gridsize.x);
				int randy = random.nextInt(0, gridsize.y);
				for (Snake snake : grid.snakes) {
					snakeSize += snake.getPositions().size();
					for (Vector pos : snake.getPositions()) {
						if (new Vector(randx, randy).equals(pos)) {
							spawnInSnake = true;
						}
					}
				}
				int totalWalls = 0;

				if (wallHandler.isEnabled()) {
					for (Wall wall : grid.walls) {
						totalWalls += wall.getNumberOfWalls();
						if (new Vector(randx, randy).equals(wall.getSnakePos())) {
							spawnInSnake = true;
						}
						for (int i = 1; i < wall.getSize().x; i++) {
							if (new Vector(randx, randy)
									.equals(new Vector(wall.getSnakePos().x + i, wall.getSnakePos().y))) {
								spawnInSnake = true;
							}
						}
						for (int i = 1; i < wall.getSize().y; i++) {
							if (new Vector(randx, randy)
									.equals(new Vector(wall.getSnakePos().x, wall.getSnakePos().y + i))) {
								spawnInSnake = true;
							}
						}
					}
				}

				if (snakeSize >= gridsize.x * gridsize.y - totalWalls) {
					snakeCoversFullScreen = true;
				}
				if (gridsize.x * gridsize.y - snakeSize - totalWalls - fruits.size() <= 0){
					snakeCoversFullScreen = true;
				}

				if (snakeCoversFullScreen) {
					break;
				}
				Rectangle rectangle = shower[0][0];
				for (Fruit fruit : fruits) {
					if (new Vector(fruit.getSnakePos().x, fruit.getSnakePos().y).equals(new Vector(randx, randy))){
					spawnInFruit = true;
					}
				}
				if (!spawnInSnake && ! spawnInFruit) {
					boolean golden = random.nextInt(0,100) + 1 <= goldenFruitHandler.getChance();
					Texture sprite = golden ? goldenAppleSprite : appleSprite;
						fruits.add(new Fruit(new Vector((int) (randx), (int) (randy)), sprite, new Vector(
								(int) ((rectangle.x - (Gdx.graphics.getWidth() / 2)) + grid.squareSize * randx),
								(int) ((rectangle.y - (Gdx.graphics.getHeight() / 2))
										+ grid.squareSize * randy)), 30, 1, golden));

				} else {
					k--;
				}
			}
		}
	}

	private void InputBoxShower(InputBox inputBox, Vector position, Vector size) {
		Rectangle[] rects = inputBox.show(position, size);
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.WHITE);
		shape.rect(rects[0].x, rects[0].y, rects[0].width, rects[0].height);
		shape.setColor(Color.BLACK);
		shape.rect(rects[1].x, rects[1].y, rects[1].width, rects[1].height);
		shape.end();

		parameter.size = size.y;
		font3 = generator.generateFont(parameter); // font size 12 pixels
		font3.setColor(Color.ORANGE);
		scoreText = new GlyphLayout();
		scoreText.setText(font3, inputBox.getString());
		int posX = (int) (rects[0].x - viewport.getScreenWidth() / 2);
		int posY = (int) (rects[0].y - viewport.getScreenHeight() / 2 + rects[0].height - 8);

		System.out.println(posX + " " + posY);
		batch.begin();
		font3.draw(batch, scoreText, posX, posY);
		batch.end();
	}

	private void drawWalls(){
		batch.begin();

		for (int i = 0; i < grid.walls.length; i++) {
			for (int j = 0; j < grid.walls[i].size.x; j++) {
				batch.draw(wallSprite, (grid.walls[i].getSpritePos().x) + j * grid.squareSize,
						(grid.walls[i].getSpritePos().y), grid.squareSize, grid.squareSize);
				for (Snake snake : grid.snakes) {
					if (snake.checkCollision(grid.walls[i].getSnakePos().add(new Vector(j, 0)))) {
						snake.isDead = true;
					}
				}
			}
			for (int j = 0; j < grid.walls[i].size.y; j++) {
				batch.draw(wallSprite, (grid.walls[i].getSpritePos().x),
						(grid.walls[i].getSpritePos().y) + j * grid.squareSize, grid.squareSize,
						grid.squareSize);
				for (Snake snake : grid.snakes) {
					if (snake.checkCollision(grid.walls[i].getSnakePos().add(new Vector(0, j)))) {
						snake.isDead = true;
					}
				}
			}

		}
		batch.end();

	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		shape.dispose();
		appleSprite.dispose();
		generator.dispose();
	}

}
