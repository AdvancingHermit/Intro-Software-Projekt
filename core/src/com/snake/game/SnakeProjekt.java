package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.snake.game.handlers.BorderHandler;
import com.snake.game.handlers.CherryHandler;
import com.snake.game.handlers.CoffeeBeanHandler;
import com.snake.game.handlers.DragonFruitHandler;
import com.snake.game.handlers.GoldenFruitHandler;
import com.snake.game.handlers.MultiplayerHandler;
import com.snake.game.handlers.QuickTimeHandler;
import com.snake.game.handlers.SnakeReverseHandler;
import com.snake.game.handlers.WallHandler;
import com.snake.game.util.Button;
import com.snake.game.util.Data;
import com.snake.game.util.Highscore;
import com.snake.game.util.InputBox;
import com.snake.game.util.JSON;
import com.snake.game.util.Leaderboard;
import com.snake.game.util.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.sound.midi.SysexMessage;

public class SnakeProjekt extends ApplicationAdapter {

	enum Scene {
		Main_Scene, Main_Game, Main_Setting, Main_Enable_Features, Main_Restart
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
	BitmapFont font3;

	Texture backArrow;

	Texture appleSprite;
	Texture goldenAppleSprite;
	Texture coffeeBeanSprite;
	Texture dragonFruitSprite;
	Texture cherry1Sprite;
	Texture cherry2Sprite;

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

	boolean canClick = true;
	boolean allSnakesDead;
	int frameCounter = 0;
	InputBox inputBox;
	int boxesHeight,
			boxesWidth;
	Button backButton, startButton, featureButton, restartButton;
	Color color;

	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;

	// handlers

	WallHandler wallHandler = new WallHandler(false, "Wall");
	MultiplayerHandler multiplayerHandler = new MultiplayerHandler(false, "1 Player", 1);
	MultiplayerHandler multiplayerHandler2 = new MultiplayerHandler(false, "2 Player", 2);
	MultiplayerHandler multiplayerHandler3 = new MultiplayerHandler(false, "3 Player", 3);
	GoldenFruitHandler goldenFruitHandler = new GoldenFruitHandler(true, "Golden Apple", 0);
	CherryHandler cherryHandler = new CherryHandler(true, "Cherries", 80);
	QuickTimeHandler quickTimeHandler = new QuickTimeHandler(false, "Quicktime", 2);
	BorderHandler borderHandler = new BorderHandler(false, "Borders");
	SnakeReverseHandler snakeReverseHandler = new SnakeReverseHandler(false, "Reverse");
	CoffeeBeanHandler coffeeBeanHandler = new CoffeeBeanHandler(true, "Coffee", 100);
	DragonFruitHandler dragonFruitHandler = new DragonFruitHandler(true, "Dragon Fruit", 25, 6);

	GameFeature[] handlers = { wallHandler, goldenFruitHandler, cherryHandler, quickTimeHandler,
			borderHandler, snakeReverseHandler, coffeeBeanHandler, multiplayerHandler2, multiplayerHandler3 };
	Button[] features = new Button[handlers.length];
	// fruits

	FruitType apple;
	FruitType goldenApple;
	FruitType cherry1;
	FruitType cherry2;
	FruitType dragonFruit;
	FruitType coffeeBean;
	FruitPicker FruitPicker = new FruitPicker();

	final int coffeeSpeed = 3;
	final int coffeeDuration = 10;

	int fruitAmount = 4;

	private int n = 15;
	private int m = 15;

	Data data;

	@Override
	public void create() {

		batch = new SpriteBatch();
		backArrow = new Texture(Gdx.files.internal("Arrow.png"));
		appleSprite = new Texture((Gdx.files.internal("Apple.png")));
		goldenAppleSprite = new Texture((Gdx.files.internal("GoldenApple.png")));
		dragonFruitSprite = new Texture((Gdx.files.internal("DragonFruit.png")));
		wallSprite = new Texture((Gdx.files.internal("wall.png")));
		cherry1Sprite = new Texture((Gdx.files.internal("Cherry1.png")));
		cherry2Sprite = new Texture((Gdx.files.internal("Cherry2.png")));
		coffeeBeanSprite = new Texture((Gdx.files.internal("CoffeeBean.png")));

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
		font = generator.generateFont(parameter);
		font.setColor(Color.ORANGE);
		generator.dispose();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/snes.ttf"));
		parameter.size = 90;
		font2 = generator.generateFont(parameter);
		font2.setColor(Color.ORANGE);
		generator.dispose();

		scoreText = new GlyphLayout();
		scoreText.setText(font2, "Player 1 SCORE");
		colonText = new GlyphLayout();
		colonText.setText(font, " : ");
		scoreNumText = new GlyphLayout();

		inputBox = new InputBox(0, new Vector(100, 100), new Vector(100, 100));
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Retroville NC.ttf"));
		JSON json;
		json = new JSON("data/leaderboard.json");
		System.out.println(json);
		Leaderboard leaderboard = new Leaderboard(json);
		json = new JSON(leaderboard.forJSON());
		System.out.println(json);
		leaderboard.updateLeaderboard(new Highscore("Test", 150));
		json = new JSON(leaderboard.forJSON());
		System.out.println(json);

		// Fruits
		apple = new FruitType(appleSprite, 1, multiplayerHandler.isEnabled() ? 5 : 1, 0);
		goldenApple = new FruitType(goldenAppleSprite, 10, 1, goldenFruitHandler.getChance());
		cherry1 = new FruitType(cherry1Sprite, 10, 1, cherryHandler.getChance());
		cherry2 = new FruitType(cherry2Sprite, 0, 0, 0);
		coffeeBean = new FruitType(coffeeBeanSprite, 100, 1, coffeeBeanHandler.getChance());
		dragonFruit = new FruitType(dragonFruitSprite, 5, 0, dragonFruitHandler.getChance());

		backButton = new Button(new Vector(-screenWidth / 2 + 150, screenHeight / 2 - 200), new Vector(300, 100),
				backArrow);
		startButton = new Button(new Vector(screenWidth / 2 - screenWidth / 8, screenHeight / 2 - screenHeight / 8),
				new Vector(screenWidth / 4, screenHeight / 4),
				createFontSize((screenWidth * 4 / 5 * ("Start").length()) / (102 * (screenWidth / 1920))), "Start");
		featureButton = new Button(
				new Vector(startButton.getpos().x + screenWidth / 32, startButton.getpos().y - screenHeight / 8),
				new Vector(screenWidth / 4 - screenWidth / 16, screenHeight / 8),
				createFontSize((screenWidth * 4 / 10 * ("Features").length()) / (150 * (screenWidth / 1920))),
				"Features");
		restartButton = new Button(new Vector(screenWidth / 2 - screenWidth / 8, screenHeight / 2 - screenHeight / 8),
				new Vector(screenWidth / 4, screenHeight / 4),
				createFontSize((screenWidth * 4 / 15 * ("Play Again").length()) / (102 * (screenWidth / 1920))),
				"Play Again");
		boxesWidth = screenWidth / 6;
		boxesHeight = screenHeight / 16;
		for (int i = 0; i < features.length; i++) {
			if (i % 2 == 0) {
				features[i] = new Button(
						new Vector((screenWidth - screenWidth * 2 / 3) - boxesWidth / 2,
								screenHeight + boxesHeight / 2 - screenHeight / 4 - (screenHeight * (i / 2) / 8)),
						new Vector(boxesWidth, boxesHeight), handlers[i]);

			} else {
				features[i] = new Button(
						new Vector((screenWidth - screenWidth / 3) - boxesWidth / 2,
								screenHeight + boxesHeight / 2 - screenHeight / 4 - (screenHeight * (i / 2) / 8)),
						new Vector(boxesWidth, boxesHeight), handlers[i]);
			}
		}
	}

	public BitmapFont createFontSize(int size) {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Retroville NC.ttf"));
		parameter.size = size;
		font3 = generator.generateFont(parameter);
		font3.setColor(Color.ORANGE);
		return font3;
	}

	public void showButton(Button temp) {
		batch.begin();
		batch.draw(temp.getbackArrow(), temp.getpos().x, temp.getpos().y, temp.getSize().x, temp.getSize().y);
		batch.end();
	}

	public void showButton(Button temp, Color color) {
		shape.setColor(color);
		shape.rect(temp.getpos().x, temp.getpos().y, temp.getSize().x, temp.getSize().y);
		shape.end();
		if (temp.gethandler() != null) {
			batch.begin();
			font.draw(batch, temp.getfeatureName(), temp.getpos().x - screenWidth / 2,
					temp.getpos().y - screenHeight / 2 + Math.round(1.75 * temp.getSize().y));
			batch.end();
		} else if (temp.gettext() != null) {
			batch.begin();
			temp.getfont().draw(batch, temp.gettext(),
					temp.getpos().x - screenWidth / 2 + temp.getSize().x / 6,
					temp.getpos().y - screenHeight / 2 + temp.getSize().y * 3 / 5);
			batch.end();
		}
		shape.begin(ShapeType.Filled);
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
				fruits.clear();
				ScreenUtils.clear(0, 0, 1, 1);

				frameCounter++;

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
				if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
					canClick = true;
				}

				showButton(backButton);
				for (Button x : features) {
					if (x.gethandler().isEnabled()) {
						color = Color.GREEN;
						showButton(x, color);
					} else {
						color = Color.RED;
						showButton(x, color);
					}

				}

				if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT)
						|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) && canClick) {
					canClick = false;
					if (backButton.clickedButton()) {
						currentScene = Scene.Main_Scene;
					}
					for (Button feature : features) {
						if (feature.clickedButton()) {
							feature.toggleisEnabled();
						}
					}
					multiplayerHandler = (multiplayerHandler2.isEnabled()) ? multiplayerHandler2
							: (multiplayerHandler3.isEnabled() ? multiplayerHandler3 : multiplayerHandler);

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
				showButton(backButton);
				batch.begin();
				if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT)
						|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) && backButton.clickedButton()) {
					currentScene = Scene.Main_Scene;
				}
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
				if (allSnakesDead && (Gdx.input.isButtonPressed(Input.Buttons.LEFT)
						|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT))) {
					if (restartButton.clickedButton()) {
						System.out.println("test");
						allSnakesDead = false;
						currentScene = Scene.Main_Restart;
					}
				}
				spawnFruit(shower);
				shape.end();

				batch.begin();

				snakeUpdater(shower);

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
			case Main_Restart:
				ScreenUtils.clear(0, 0, 1, 1);
				camera.update();
				batch.setProjectionMatrix(camera.combined);
				shape.begin(ShapeType.Filled);
				shape.end();
				gridsize = new Vector(n, m);
				grid = new Grid(gridsize,
						multiplayerHandler.isEnabled() ? multiplayerHandler.getPlayerAmount() : 1,
						screenHeight);
				if (wallHandler.isEnabled()) {
					grid.walls = grid.wallGenerator(gridsize);
				}
				if (snakeReverseHandler.isEnabled()) {
					cherry1.setChance(0);
				}
				fruits.clear();
				currentScene = Scene.Main_Game;
				break;

		}

	}

	private void checkFruitCollsions() {
		Iterator<Fruit> fruitIterator = fruits.iterator();
		while (fruitIterator.hasNext()) {
			Fruit fruit = fruitIterator.next();
			for (Snake snake : grid.snakes) {
				if (snake.checkCollision(fruit.getSnakePos())) {

					if (fruit.getSprite().equals(dragonFruitSprite)) {
						snake.fireActive = true;
						snake.setFireCounter(0);

					}
					if (fruit.getSprite().equals(cherry1Sprite) || fruit.getSprite().equals(cherry2Sprite)) {
						teleportSnake(snake, fruit);

					} else if (snakeReverseHandler.isEnabled()) {

						reverseSnake(snake);

					}

					if (fruit.getSprite().equals(coffeeBeanSprite)) {
						snake.setMaxcounter(coffeeSpeed);
						snake.setSpeedCounter(coffeeDuration);
					}
					snake.setHasEaten(fruit);
					fruitIterator.remove();
				}
			}
		}

	}

	private void teleportSnake(Snake snake, Fruit eatenFruit) {
		for (Fruit fruit : fruits) {
			if (((fruit.getSprite().equals(cherry1Sprite) && eatenFruit.getSprite().equals(cherry2Sprite))
					|| ((fruit.getSprite().equals(cherry2Sprite) && eatenFruit.getSprite().equals(cherry1Sprite))))) {

				List<Vector> newPositions = snake.getPositions();
				newPositions.add((fruit.getSnakePos()));
				snake.setPositions((ArrayList<Vector>) newPositions);
				snake.setLastRemoved(newPositions.get(0));
				newPositions.remove(0);
			}
		}
	}

	private static void reverseSnake(Snake snake) {
		ArrayList<Vector> positions = snake.getPositions();
		Collections.reverse(positions);
		snake.setPositions(positions);
		Vector head = positions.get(positions.size() - 1);
		Vector second = positions.get(positions.size() - 2);
		Vector newVel = new Vector(Math.max(Math.min(head.x - second.x, 1), -1),
				Math.max(Math.min(head.y - second.y, 1), -1));

		if (!newVel.equals(snake.getVel())) {
			snake.setVel(newVel);
			snake.setKey(snake.keyVectorMapReversed.get(newVel));
		}
	}

	private void snakeUpdater(Rectangle[][] shower) {
		int deadSnakeCounter = 0;
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
						snake.checkCollision();
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
						snake.checkCollision();

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
					if (snake.fireActive) {

						ParticleEffect effect = snake.getEffect();
						if (snake.getFireCounter() == 0) {
							effect = scaleEffect(effect);
						}

						effect.setPosition(
								shower[cx][cy].x - screenWidth / 2 + grid.squareSize / 2
										+ snake.getVel().x * (grid.squareSize / 2),
								shower[cx][cy].y - screenHeight / 2 + grid.squareSize / 2
										+ snake.getVel().y * (grid.squareSize / 2));

						effect.getEmitters().first().getAngle().setHigh((int) snake.getVel().angle() - 10,
								(int) snake.getVel().angle() + 10);
						effect.getEmitters().first().getAngle().setLow((int) snake.getVel().angle() - 10,
								(int) snake.getVel().angle() + 10);
						if (!snake.getVel().equals(snake.getOldVel())) {
							effect.reset();
							effect = scaleEffect(effect);
							effect.start();
						}
						effect.draw(batch, Gdx.graphics.getDeltaTime());
						snake.dragonFruit();

						if (snake.getFireCounter() >= 30 * dragonFruitHandler.getFireTime()) {
							snake.fireActive = false;
							snake.setFireCounter(0);
						}

					}

				} else {
					batch.draw(snakeBodySprite, (int) (shower[cx][cy].x - screenWidth / 2),
							(int) (shower[cx][cy].y - screenHeight / 2), grid.squareSize, grid.squareSize);
				}
			}
			if (quickTimeHandler.isEnabled()) {
				Vector snakeVel = snake.getVel();

				if (snakeVel.equals(snake.getOldVel())
						&& snake.getQuickTimeCounter() >= 30 * quickTimeHandler.getTime() && !snake.isDead) {

					snake.isDead = true;
					snake.setQuickTimeCounter(0);
				} else if (!snakeVel.equals(snake.getOldVel())) {
					snake.setQuickTimeCounter(0);
				}
				if (!snake.isDead) {
					snake.quickTime();
				}

			}
			batch.setColor(Color.CHARTREUSE);
			if (snake.isDead) {
				deadSnakeCounter++;
			}
		}
		batch.end();
		if (deadSnakeCounter == grid.snakes.length) {
			shape.begin(ShapeType.Filled);
			showButton(restartButton, color);
			shape.end();
			allSnakesDead = true;
		}
		batch.begin();
		batch.setColor(Color.WHITE);

	}

	private void spawnFruit(Rectangle[][] shower) {
		boolean cherry1Spawned = false;
		boolean cherry2Spawned = false;
		if (fruits.isEmpty()) {
			for (int k = 0; k < fruitAmount; k++) {
				boolean snakeCoversFullScreen = false;
				boolean validSpawn = true;
				int snakeSize = 0;
				Vector spawningPosition = new Vector(random.nextInt(0, gridsize.x), random.nextInt(0, gridsize.y));
				for (Snake snake : grid.snakes) {
					if (snake.isDead && (borderHandler.isEnabled() || wallHandler.isEnabled())) {
						fruits.removeIf(fruit -> fruit.getSnakePos().equals(snake.getPositions().get(0)));
					}
					snakeSize += snake.getPositions().size();
					if (snake.getPositions().contains(spawningPosition)) {
						validSpawn = false;
					}
				}
				int totalWalls = 0;

				if (wallHandler.isEnabled()) {
					for (Wall wall : grid.walls) {
						if (wall.getOccupiedTiles().contains(spawningPosition)) {
							validSpawn = false;
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
						validSpawn = false;
						break;
					}
				}
				if (validSpawn) {


					if (cherry1Spawned && !cherry2Spawned) {

						createFruit(cherry2, spawningPosition, rectangle);
						cherry2Spawned = true;
						continue;
					}

					FruitType spawningFruitType = FruitPicker.pickFruitType(apple, goldenApple, cherry1, dragonFruit,
							coffeeBean);
					if (cherry1Spawned && spawningFruitType.equals(cherry1)) {
						k--;
						continue;
					}

					if (spawningFruitType.equals(cherry1)) {
						cherry1Spawned = true;
						k--;
					}
					createFruit(spawningFruitType, spawningPosition, rectangle);

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

	public ParticleEffect scaleEffect(ParticleEffect effect) {
		effect.getEmitters().first().getXScale().setHigh((int) (grid.squareSize / 1.4));
		effect.getEmitters().first().getYScale().setLow((int) (grid.squareSize / 1.4));
		effect.getEmitters().first().getLife().setHigh((int) ((grid.squareSize / 50.0) * 325));
		return effect;
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
