package com.snake.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Size;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.snake.game.util.InputBox;
import com.snake.game.util.Vector;
import com.snake.game.handlers.BorderHandler;
import com.snake.game.handlers.GoldenFruitHandler;
import com.snake.game.handlers.MultiplayerHandler;
import com.snake.game.handlers.QuickTimeHandler;
import com.snake.game.handlers.SnakeReverseHandler;
import com.snake.game.handlers.WallHandler;
import com.snake.game.util.Button;
import com.badlogic.gdx.math.Rectangle;

public class SnakeProjekt extends ApplicationAdapter {

	enum Scene {
		Main_Scene, Main_Game, Main_Setting, Main_Enable_Features
	}


	Scene currentScene = Scene.Main_Scene;

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
	int backButtonHeight, backButtonWidth, backButtonX, backButtonY, startButtonWidth, startButtonHeight, boxesHeight,
			boxesWidth;
	Button backButton, startButton, featureButton;
	Color color;
	Button[] features = new Button[12];

	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;





  //handlers
	WallHandler wallHandler = new WallHandler(false);
	MultiplayerHandler multiplayerHandler = new MultiplayerHandler(false, 2);
	GoldenFruitHandler goldenFruitHandler = new GoldenFruitHandler(true, 100);
	QuickTimeHandler quickTimeHandler = new QuickTimeHandler(false, 2);
	BorderHandler borderHandler = new BorderHandler(true);
	SnakeReverseHandler snakeReverseHandler = new SnakeReverseHandler(false);
  
  //fruits
	FruitType apple;
	FruitType goldenApple;
	FruitType cherry;
    int fruitAmount = 2;
  
	private int n = 15;
	private int m = 15;

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

		inputBox = new InputBox(0, new Vector(100, 100), new Vector(100, 100));
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Retroville NC.ttf"));

		backButtonX = -screenWidth / 2 + 150;
		backButtonY = screenHeight / 2 - 200;
		backButtonWidth = 300;
		backButtonHeight = 100;

		//Fruits
		apple = new FruitType(appleSprite, 1, 1);
		goldenApple = new FruitType(goldenAppleSprite, 10, 1);
		cherry = new FruitType(goldenAppleSprite, 10, 0);


		backButton = new Button(new Vector(-screenWidth / 2 + 150, screenHeight / 2 - 200), new Vector(300, 100),
				backArrow);
		startButton = new Button(new Vector(screenWidth / 2 - screenWidth / 8, screenHeight / 2 - screenHeight / 8),
				new Vector(screenWidth / 4, screenHeight / 4));
		featureButton = new Button(
				new Vector(startButton.getpos().x + screenWidth / 32, startButton.getpos().y - screenHeight / 8),
				new Vector(screenWidth / 4 - screenWidth / 16, screenHeight / 8));
		boxesWidth = screenWidth / 6;
		boxesHeight = screenHeight / 16;
		for (int i = 0; i < features.length; i++) {
			if (i % 2 == 0) {
				features[i] = new Button(
						new Vector((screenWidth - screenWidth * 2 / 3) - boxesWidth / 2,
								screenHeight + boxesHeight / 2 - screenHeight / 4 - (screenHeight * (i / 2) / 8)),
						new Vector(boxesWidth, boxesHeight));
			} else {
				features[i] = new Button(
						new Vector((screenWidth - screenWidth / 3) - boxesWidth / 2,
								screenHeight + boxesHeight / 2 - screenHeight / 4 - (screenHeight * (i / 2) / 8)),
						new Vector(boxesWidth, boxesHeight));
			}
		}
	}

	public void showButton(Button temp) {
		batch.begin();
		batch.draw(temp.getbackArrow(), temp.getpos().x, temp.getpos().y, temp.getSize().x, temp.getSize().y);
		batch.end();
	}

	public void showButton(Button temp, Color color) {
		shape.setColor(color);
		shape.rect(temp.getpos().x, temp.getpos().y, temp.getSize().x, temp.getSize().y);

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		switch (currentScene) {
			case Main_Scene:
				ScreenUtils.clear(0, 0, 1, 1);

				frameCounter++;
				mousePressed = false;
				camera.update();
				batch.setProjectionMatrix(camera.combined);
				shape.begin(ShapeType.Filled);
				color = Color.GREEN;
				showButton(startButton, color);
				color = Color.RED;
				showButton(featureButton, color);

				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
					if (startButton.clickedButton()) {
						gridsize = new Vector(n, m);
						grid = new Grid(gridsize,
								multiplayerHandler.isEnabled() ? multiplayerHandler.getPlayerAmount() : 1,
								screenHeight);
						if (wallHandler.isEnabled()) {
							grid.walls = grid.wallGenerator(gridsize);
						}
						currentScene = Scene.Main_Game;
					} else if (featureButton.clickedButton()) {
						currentScene = Scene.Main_Enable_Features;
					}
					mousePressed = true;
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
				color = Color.RED;
				showButton(backButton);
				for (Button x : features) {
					showButton(x, color);
				}

				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
					mousePressed = true;
					if (backButton.clickedButton()) {
						currentScene = Scene.Main_Scene;
					}
					for (int i = 0; i < features.length; i++) {
						if (features[i].clickedButton()) {
							switch (i) {
								case (0):
									wallHandler.toggle();
									break;
								case (1):
									multiplayerHandler.toggle();
									break;
								case (2):
									goldenFruitHandler.toggle();
									break;
								case (3):
									quickTimeHandler.toggle();
									break;
								case (4):
									borderHandler.toggle();
									break;
								case (5):
									snakeReverseHandler.toggle();
									break;
								case (6):
									break;
								case (7):

									break;
								case (8):

									break;
								case (9):

									break;
								case (10):

									break;
								case (11):

									break;
								default:
									break;
							}
						}
					}

				}
				if (frameCounter % 3 == 0) {
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
			for (Snake snake : grid.snakes) {
				if (snake.checkCollision(fruit.getSnakePos())) {
					if (snakeReverseHandler.isEnabled()) {
						reverseSnake(snake);
					}
					if (fruit.getSprite().equals(goldenAppleSprite)){
						teleportSnake(snake, fruit);
					}
					snake.setHasEaten(fruit);
					fruitIterator.remove();
				}
			}
		}

	}

	private void teleportSnake(Snake snake, Fruit eatenFruit) {
		for (Fruit fruit : fruits){
			if (fruit.getSprite().equals(eatenFruit.getSprite()) && !fruit.getSnakePos().equals(eatenFruit.getSnakePos())){
				List<Vector> newPositions = snake.getPositions();
				newPositions.add((fruit.getSnakePos()));
				newPositions.remove(0);
				snake.setPositions((ArrayList<Vector>) newPositions);
				snake.setGrow(snake.getGrow()- fruit.getGrowth());
			}
		}
	}

	private static void reverseSnake(Snake snake) {
		ArrayList<Vector> positions = snake.getPositions();
		Collections.reverse(positions);
		snake.setPositions(positions);
		Vector head = positions.get(positions.size() - 1);
		Vector second = positions.get(positions.size() - 2);
		Vector newVel = new Vector(Math.max(Math.min(head.x - second.x, 1), -1), Math.max(Math.min(head.y - second.y, 1), -1));
		if (!newVel.equals(snake.getVel())) {
			snake.setVel(newVel);
			snake.setKey(snake.keyVectorMapReversed.get(newVel));
		}
	}

	private void snakeUpdater(Rectangle[][] shower) {
		for (Snake snake : grid.snakes) {
			ArrayList<Vector> positions = snake.getPositions();
			for (int k = 0; k < positions.size(); k++) {
				int cx = positions.get(k).x;
				int cy = positions.get(k).y;

				if (cx == grid.gridSize.x || cx == -1) {
					if (borderHandler.isEnabled()) {
						snake.isDead = true;
						snake.moveBack();
						cx = positions.get(k).x;
						cy = positions.get(k).y;
					} else {
						// Skiftes til i, når vi looper over slanger.
						cx = positions.get(k).x = grid.gridSize.x - Math.abs(cx);
						snake.move();
					}
				}
				if (cy == grid.gridSize.y || cy == -1) {
					if (borderHandler.isEnabled()) {
						snake.isDead = true;
						snake.moveBack();
						cx = positions.get(k).x;
						cy = positions.get(k).y;
					} else {
						// Skiftes til i, når vi looper over slanger.
						cy = positions.get(k).y = grid.gridSize.y - Math.abs(cy);
						snake.move();
					}
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
			if (quickTimeHandler.isEnabled()) {
				Vector snakeVel = snake.getVel();

				if (snakeVel.equals(snake.getQuickTimeOldVel())
						&& snake.getQuickTimeCounter() >= 30 * quickTimeHandler.getTime() && !snake.isDead) {

					snake.isDead = true;
					snake.setQuickTimeCounter(0);
				} else if (!snakeVel.equals(snake.getQuickTimeOldVel())) {
					snake.setQuickTimeCounter(0);
				}
				if (!snake.isDead) {
					snake.quickTime();
				}

			}

		}
	}

	private void spawnFruit(Rectangle[][] shower) {
		if (fruits.isEmpty()) {
			for (int k = 0; k < fruitAmount; k++) {
				boolean snakeCoversFullScreen = false;
				boolean invalidSpawn = false;
				int snakeSize = 0;
				Vector spawningPosition = new Vector(random.nextInt(0, gridsize.x), random.nextInt(0, gridsize.y));
				for (Snake snake : grid.snakes) {
					if (snake.isDead && (borderHandler.isEnabled() || wallHandler.isEnabled())){
						fruits.removeIf(fruit -> fruit.getSnakePos().equals(snake.getPositions().get(0)));
					}
					snakeSize += snake.getPositions().size();
					if (snake.getPositions().contains(spawningPosition)) {
						invalidSpawn = true;
					}
				}
				int totalWalls = 0;

				if (wallHandler.isEnabled()) {
					for (Wall wall : grid.walls) {
						if (wall.getOccupiedTiles().contains(spawningPosition)) {
							invalidSpawn = true;
						}
					}
				}

				if (snakeSize >= gridsize.x * gridsize.y - totalWalls) {
					snakeCoversFullScreen = true;
				}
				if (gridsize.x * gridsize.y - snakeSize - totalWalls - fruits.size() <= 0) {
					snakeCoversFullScreen = true;
				}

				if (snakeCoversFullScreen) {
					break;
				}
				Rectangle rectangle = shower[0][0];
				for (Fruit fruit : fruits) {
					if (fruit.getSnakePos().equals(spawningPosition)) {
						invalidSpawn = true;
						break;
					}
				}
				if (!invalidSpawn) {
					boolean golden = (random.nextInt(0,100) + 1 <= goldenFruitHandler.getChance()) && goldenFruitHandler.isEnabled();
                    if (golden) {
                        createFruit(goldenApple, spawningPosition, rectangle);
                    } else {
                        createFruit(apple, spawningPosition, rectangle);
                    }



                } else {
					k--;
				}
			}
		}
	}

	private void createFruit(FruitType fruitType, Vector spawningPosition, Rectangle rectangle) {
		fruits.add(new Fruit(spawningPosition, fruitType.getSprite(), new Vector(
				(int) ((rectangle.x - (Gdx.graphics.getWidth() / 2)) + grid.squareSize * spawningPosition.x),
				(int) ((rectangle.y - (Gdx.graphics.getHeight() / 2)) + grid.squareSize * spawningPosition.y)),
				fruitType.getScore(), fruitType.getGrowth()));
	}

	private void InputBoxShower(InputBox inputBox) {
		inputBox.enable(screenHeight);
		inputBox.update();
		Rectangle[] rects = inputBox.show();
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.WHITE);
		shape.rect(rects[0].x, rects[0].y, rects[0].width, rects[0].height);
		shape.setColor(Color.BLACK);
		shape.rect(rects[1].x, rects[1].y, rects[1].width, rects[1].height);
		shape.end();

		Object[] reciever = inputBox.getFont();
		BitmapFont fontReceived = (BitmapFont) reciever[0];
		GlyphLayout glyphReceived = (GlyphLayout) reciever[1];
		int posX = (int) (rects[0].x - viewport.getScreenWidth() / 2);
		int posY = (int) (rects[0].y - viewport.getScreenHeight() / 2 + rects[0].height - 8);
		batch.begin();
		fontReceived.draw(batch, glyphReceived, posX, posY);
		batch.end();
	}

	private void drawWalls() {
		batch.begin();

		for (int i = 0; i < grid.walls.length; i++) {
			for (int j = 0; j < grid.walls[i].size.x; j++) {
				batch.draw(wallSprite, (grid.walls[i].getSpritePos().x) + j * grid.squareSize,
						(grid.walls[i].getSpritePos().y), grid.squareSize, grid.squareSize);
				for (Snake snake : grid.snakes) {
					if (snake.checkCollision(grid.walls[i].getSnakePos().add(new Vector(j, 0)))) {
						snake.isDead = true;
						snake.moveBack();
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
						snake.moveBack();
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
