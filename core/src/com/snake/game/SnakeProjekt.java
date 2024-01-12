package com.snake.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.Particle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.snake.game.handlers.*;
import com.snake.game.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.sound.midi.SysexMessage;

public class SnakeProjekt extends ApplicationAdapter {

	enum Scene {
		Main_Scene, Login, Main_Game, Main_Setting, Main_Enable_Features, Main_Restart
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
	BitmapFont featureFont;
	BitmapFont loginFont;
	BitmapFont loginContinueFont;
	BitmapFont mainScreenFont;
	BitmapFont score;

	Texture backArrow;
	Texture settings;
	Texture appleSprite;
	Texture goldenAppleSprite;
	Texture coffeeBeanSprite;
	Texture dragonFruitSprite;
	Texture cherry1Sprite;
	Texture cherry2Sprite;

	Texture wallSprite;
	Texture snakeBodySprite;
	Texture snakeBodySidewaysSprite;
	Texture snakeBodyCornerSprite;
	Texture snakeHeadSprite;
	Texture snakeHeadSidewaysSprite;
	Texture snakeHeadCoffeeSprite;
	Texture snakeHeadCoffeeSidewaysSprite;
	Texture PlayerOne, PlayerTwo, PlayerThree;
	Texture timerSprite;

	List<Fruit> fruits = new ArrayList<>();
	Random random = new Random();
	GlyphLayout scoreNumText;
	GlyphLayout colonText;
	GlyphLayout scoreText;
	GlyphLayout quickTimerText;
	GlyphLayout snakeText;

	double screenHeight;
	double screenWidth;
	int maxcounter = 15;

	boolean canClick = true;
	boolean allSnakesDead;
	boolean showControlls;
	int frameCounter = 0;
	InputBox inputBox;
	int boxesHeight,
			boxesWidth;
	Button backButton, startButton, featureButton, controlsSettingsButton, restartButton, settingsButton, settingsRect,
			nTextRect, mTextRect, setSizesSettingsButton, Player1, Player2, Player3, snakeSpeedRect, loginButton;
	InputBox updateSnakeSpeed, updateM, updateN;
	Color color;

	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;

	// handlers

	WallHandler wallHandler = new WallHandler(false, "Wall");
	MultiplayerHandler multiplayerHandler = new MultiplayerHandler(false, "1 Player", 1);
	MultiplayerHandler multiplayerHandler2 = new MultiplayerHandler(false, "2 Player", 2);
	MultiplayerHandler multiplayerHandler3 = new MultiplayerHandler(false, "3 Player", 3);
	GoldenFruitHandler goldenFruitHandler = new GoldenFruitHandler(true, "Golden Apple", 20);
	CherryHandler cherryHandler = new CherryHandler(true, "Cherries", 80);
	QuickTimeHandler quickTimeHandler = new QuickTimeHandler(true, "Quicktime", 10);
	BorderHandler borderHandler = new BorderHandler(false, "Borders");
	SnakeReverseHandler snakeReverseHandler = new SnakeReverseHandler(false, "Reverse");
	CoffeeBeanHandler coffeeBeanHandler = new CoffeeBeanHandler(true, "Coffee", 100, 3, 10);
	DragonFruitHandler dragonFruitHandler = new DragonFruitHandler(true, "Dragon Fruit", 25, 6);

	GameFeature[] handlers = { wallHandler, borderHandler, quickTimeHandler, snakeReverseHandler, goldenFruitHandler,
			cherryHandler, dragonFruitHandler,
			coffeeBeanHandler, multiplayerHandler2, multiplayerHandler3 };
	Button[] features = new Button[handlers.length];
	// fruits

	FruitType apple;
	FruitType goldenApple;
	FruitType cherry1;
	FruitType cherry2;
	FruitType dragonFruit;
	FruitType coffeeBean;
	FruitPicker FruitPicker = new FruitPicker();

	int fruitAmount = 4;
	InputBox input;
	InputBox loginInput;

	private int n = 15;
	private int m = 15;

	Leaderboard leaderboard;
	ParticleEffect effect = new ParticleEffect();
	String username;

	Vector loginBoxSize;
	Vector loginBoxPos;

	Users users;
	JSON json;

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
		settings = new Texture((Gdx.files.internal("settings.png")));
		snakeBodySprite = new Texture((Gdx.files.internal("snakebody.png")));
		snakeBodySidewaysSprite = new Texture((Gdx.files.internal("snakebodysideways.png")));
		snakeBodyCornerSprite = new Texture((Gdx.files.internal("snakebodycorner.png")));
		snakeHeadSprite = new Texture((Gdx.files.internal("snakehead.png")));
		snakeHeadSidewaysSprite = new Texture((Gdx.files.internal("snakeheadsideways.png")));
		snakeHeadCoffeeSprite = new Texture((Gdx.files.internal("snakeheadcoffee.png")));
		snakeHeadCoffeeSidewaysSprite = new Texture((Gdx.files.internal("snakeheadcoffeesideways.png")));
		PlayerOne = new Texture((Gdx.files.internal("PlayerOne.png")));
		PlayerTwo = new Texture((Gdx.files.internal("PlayerTwo.png")));
		PlayerThree = new Texture((Gdx.files.internal("PlayerThree.png")));
		timerSprite = new Texture((Gdx.files.internal("Timer.png")));

		img = new Texture("badlogic.jpg");
		shape = new ShapeRenderer();

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		// camera.setToOrtho(false, 1920, 1080);
		viewport = new FitViewport((int) screenWidth, (int) screenHeight, camera);

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
		featureFont = createFont(
				(int) (((double) screenWidth * 4 / 5.0f * 4.0f) / (102 * ((double) screenWidth / 1920.0f))));
		mainScreenFont = createFont(200, Color.BLACK);

		scoreText = new GlyphLayout();
		scoreText.setText(font2, "Player 1 SCORE");
		quickTimerText = new GlyphLayout();
		quickTimerText.setText(font2, "Player 1 Time");
		colonText = new GlyphLayout();
		colonText.setText(font, " : ");
		scoreNumText = new GlyphLayout();
		snakeText = new GlyphLayout();

		inputBox = new InputBox(0, new Vector(100, 100), new Vector(100, 100));
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Retroville NC.ttf"));

		// Fruits
		apple = new FruitType(appleSprite, 1, multiplayerHandler.isEnabled() ? 5 : 1, 0, cherryHandler);
		goldenApple = new FruitType(goldenAppleSprite, 10, -1, goldenFruitHandler.getChance(), goldenFruitHandler);
		cherry1 = new FruitType(cherry1Sprite, 10, 1, cherryHandler.getChance(), cherryHandler);
		cherry2 = new FruitType(cherry2Sprite, 0, 0, 0, cherryHandler);
		coffeeBean = new FruitType(coffeeBeanSprite, 100, 1, coffeeBeanHandler.getChance(), coffeeBeanHandler);
		dragonFruit = new FruitType(dragonFruitSprite, 5, 0, dragonFruitHandler.getChance(), dragonFruitHandler);

		// making setting scene
		settingsRect = new Button(
				new Vector((int) (screenWidth / 2 - screenWidth / 4), (int) (screenHeight / 2 + screenHeight / 8)),
				new Vector((int) (screenWidth / 2), (int) (screenHeight / 8)),
				createFont((int) ((screenWidth * 4 / 10 * ("Gridsize").length()) / (102 * (screenWidth / 1920)))),
				"Gridsize");
		nTextRect = new Button(new Vector((int) (screenWidth / 2 - screenWidth / 4), (int) (screenHeight / 2)),
				new Vector((int) (screenWidth / 4), (int) (screenHeight / 8)),
				createFont((int) ((102 * screenWidth) / 1920)),
				"n:");
		mTextRect = new Button(new Vector((int) (screenWidth / 2), (int) (screenHeight / 2)),
				new Vector((int) (screenWidth / 4), (int) (screenHeight / 8)),
				createFont((int) ((102 * screenWidth) / 1920)),
				"m:");

		// Repeat the same for the rest of the objects...

		snakeSpeedRect = new Button(
				new Vector((int) (screenWidth / 2 - screenWidth / 4), (int) (screenHeight / 2 - screenHeight / 4)),
				new Vector((int) (screenWidth / 2), (int) (screenHeight / 6)),
				createFont((int) ((screenWidth * 4 / 15 * ("Snake Speed:").length()) / (150 * (screenWidth / 1920)))),
				"Snake Speed:");

		updateN = new InputBox(1, new Vector((int) (screenWidth / 2 - screenWidth / 8), (int) (screenHeight / 2)),
				new Vector((int) (screenWidth / 8), (int) (screenHeight / 16)));
		updateM = new InputBox(1, new Vector((int) (screenWidth / 2 + screenWidth / 8), (int) (screenHeight / 2)),
				new Vector((int) (screenWidth / 8), (int) (screenHeight / 16)));
		updateSnakeSpeed = new InputBox(1,
				new Vector((int) (screenWidth / 2 + screenWidth / 24), (int) (screenHeight / 2 - screenHeight / 5)),
				new Vector((int) (screenWidth / 5), (int) (screenHeight / 12)));
		Player1 = new Button(new Vector((int) (screenWidth / 6), (int) (screenHeight / 2 + screenHeight / 6)),
				new Vector((int) (screenWidth / 6), (int) (screenHeight / 12)),
				createFont((int) ((screenWidth * 4 / 15 * ("Snake Speed:").length()) / (150 * (screenWidth / 1920)))),
				"Player 1");
		Player2 = new Button(
				new Vector((int) (screenWidth / 2 - screenWidth / 12), (int) (screenHeight / 2 + screenHeight / 6)),
				new Vector((int) (screenWidth / 6), (int) (screenHeight / 12)),
				createFont((int) ((screenWidth * 4 / 15 * ("Snake Speed:").length()) / (150 * (screenWidth / 1920)))),
				"Player 2");
		Player3 = new Button(
				new Vector((int) (screenWidth / 2 + screenWidth / 6), (int) (screenHeight / 2 + screenHeight / 6)),
				new Vector((int) (screenWidth / 6), (int) (screenHeight / 12)),
				createFont((int) ((screenWidth * 4 / 15 * ("Snake Speed:").length()) / (150 * (screenWidth / 1920)))),
				"Player 3");

		// Making Buttons
		backButton = new Button(new Vector((int) (-screenWidth / 2 + 150), (int) (screenHeight / 2 - 250)),
				new Vector((int) (300), (int) (100)),
				backArrow);
		settingsButton = new Button(new Vector((int) (screenWidth / 2 - 300), (int) (screenHeight / 2 - 300)),
				new Vector((int) (200), (int) (200)),
				settings);
		controlsSettingsButton = new Button(
				new Vector((int) (screenWidth / 2 - screenWidth / 4), (int) (screenHeight - screenHeight / 5)),
				new Vector((int) (screenWidth / 2), (int) (screenHeight / 10)),
				createFont((int) ((screenWidth * 4 / 5 * ("Controls").length()) / (172 * (screenWidth / 1920)))),
				"Controls");
		setSizesSettingsButton = new Button(
				new Vector((int) (screenWidth / 2 - screenWidth / 4), (int) (screenHeight - screenHeight / 5)),
				new Vector((int) (screenWidth / 2), (int) (screenHeight / 10)),
				createFont((int) ((screenWidth * 4 / 5 * ("Gameplay").length()) / (172 * (screenWidth / 1920)))),
				"Gameplay");
		startButton = new Button(
				new Vector((int) (screenWidth / 2 - screenWidth / 8), (int) (screenHeight / 2 - screenHeight / 8)),
				new Vector((int) (screenWidth / 4), (int) (screenHeight / 4)),
				createFont((int) ((screenWidth * 4 / 5 * ("Start").length()) / (102 * (screenWidth / 1920))),
						normColor(0, 0, 0, 255)),
				"START");
		featureButton = new Button(
				new Vector((int) (startButton.getpos().x + screenWidth / 32),
						(int) (startButton.getpos().y - screenHeight / 8)),
				new Vector((int) (screenWidth / 4 - screenWidth / 16), (int) (screenHeight / 8)),
				createFont((int) ((screenWidth * 4 / 10 * ("Features").length()) / (150 * (screenWidth / 1920)))),
				"Features");
		restartButton = new Button(
				new Vector((int) (screenWidth / 2 - screenWidth / 8), (int) (screenHeight / 2 - screenHeight / 8)),
				new Vector((int) (screenWidth / 4), (int) (screenHeight / 4)),
				createFont((int) ((screenWidth * 4 / 15 * ("Play Again").length()) / (102 * (screenWidth / 1920)))),
				"Play Again");
		boxesWidth = (int) (screenWidth / 6);
		boxesHeight = (int) (screenHeight / 16);
		for (int i = 0; i < features.length; i++) {
			if (i % 2 == 0) {
				features[i] = new Button(
						new Vector((int) ((screenWidth - screenWidth * 2 / 3) - boxesWidth / 2),
								(int) (screenHeight + boxesHeight / 2 - screenHeight / 4
										- (screenHeight * (i / 2) / 6))),
						new Vector((int) (boxesWidth), (int) (boxesHeight)), handlers[i]);

			} else {
				features[i] = new Button(
						new Vector((int) ((screenWidth - screenWidth / 3) - boxesWidth / 2),
								(int) (screenHeight + boxesHeight / 2 - screenHeight / 4
										- (screenHeight * (i / 2) / 6))),
						new Vector((int) (boxesWidth), (int) (boxesHeight)), handlers[i]);
			}
		}

		effect.load(Gdx.files.internal("particles/fire.p"), Gdx.files.internal("particles"));

		json = new JSON("data/data.json");
		leaderboard = new Leaderboard(json);

		// Login Screen Definitions
		loginFont = createFont((int) ((screenWidth * 4) / (102 * (screenWidth / 1920))), Color.BLACK);
		loginContinueFont = createFont((int) ((screenWidth * 2) / (102 * (screenWidth / 1920))), Color.BLACK);
		loginBoxSize = new Vector((int) (screenWidth / 2), (int) (screenHeight / 2));
		loginBoxPos = new Vector((int) (screenWidth / 2 - loginBoxSize.x / 2),
				(int) (screenHeight / 2 - loginBoxSize.y / 2));

		Vector loginInputSize = new Vector(loginBoxSize.x / 2, loginBoxSize.y / 8);
		Vector loginInputPos = new Vector(loginBoxPos.x + loginBoxSize.x / 2 - loginInputSize.x / 2,
				loginBoxPos.y + loginBoxSize.y / 2 - loginInputSize.y);
		loginInput = new InputBox(0, new Vector(loginInputPos.x, loginInputPos.y),
				new Vector(loginInputSize.x, loginInputSize.y));
		Vector loginButtonSize = new Vector(loginInputSize.x / 2, loginInputSize.y);
		loginButton = new Button(
				new Vector(loginInputPos.x + (loginButtonSize.x / 2),
						(loginInputPos.y - loginButtonSize.y - (loginButtonSize.y / 2))),
				loginButtonSize, loginContinueFont, "Continue");

		users = new Users(json);

		score = createFont(50);

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render() {

		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			if (this.grid != null) {
				saveScore();
			}

			for (int i = 0; i < leaderboard.forJSON().length; i++) {
				System.out.println(leaderboard.forJSON()[i]);
			}

			json.addStringData(leaderboard.forJSON());
			json.addStringData(users.forJSON());
			json.createFile("data/");
			Gdx.app.exit();
		}
		switch (currentScene) {
			case Main_Scene:
				fruits.clear();
				// ScreenUtils.clear(0, 0, 1, 1);
				ScreenUtils.clear(0, 0, 1, 1);
				drawMainScreen();

				frameCounter++;

				camera.update();
				batch.setProjectionMatrix(camera.combined);
				shape.begin(ShapeType.Filled);
				color = Color.GREEN;
				showButton(startButton, color);
				color = Color.RED;
				showButton(featureButton, color);
				showButton(settingsButton);

				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
					if (startButton.clickedButton()) {
						currentScene = Scene.Login;

					} else if (featureButton.clickedButton()) {
						currentScene = Scene.Main_Enable_Features;
					} else if (settingsButton.clickedButton()) {
						currentScene = Scene.Main_Setting;
					}
				}
				shape.end();
				break;
			case Login:
				ScreenUtils.clear(0, 0, 1, 1);
				camera.update();
				batch.setProjectionMatrix(camera.combined);
				shape.begin(ShapeType.Filled);

				shape.setColor(Color.YELLOW);
				shape.rect(loginBoxPos.x, loginBoxPos.y, loginBoxSize.x, loginBoxSize.y);
				shape.end();
				batch.begin();
				Vector loginOffset = getTextSize(loginFont, "Username:");
				loginFont.draw(batch, "Username:", -loginOffset.x / 2, loginOffset.y * 2);
				batch.end();
				shape.begin(ShapeType.Filled);
				showButton(loginButton, normColor(0, 118, 242, 255));
				shape.end();
				inputBoxShower(loginInput);
				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
					if (loginButton.clickedButton()) {
						username = loginInput.getString();
						currentScene = Scene.Main_Game;
						gridsize = new Vector(n, m);
						grid = new Grid(gridsize,
								multiplayerHandler.isEnabled() ? multiplayerHandler.getPlayerAmount() : 1,
								(int) screenHeight);
						if (wallHandler.isEnabled()) {
							grid.walls = grid.wallGenerator(gridsize);
						}
					}
				}

				break;
			case Main_Setting:
				if (!showControlls) {
					ScreenUtils.clear(0, 0, 1, 1);
					camera.update();
					batch.setProjectionMatrix(camera.combined);
					shape.begin(ShapeType.Filled);
					showButton(backButton);
					showButton(settingsRect, Color.YELLOW);
					showButton(nTextRect, Color.YELLOW);
					showButton(mTextRect, Color.YELLOW);
					showButton(snakeSpeedRect, Color.GREEN);
					showButton(controlsSettingsButton, color);
					shape.end();
					inputBoxShower(updateM);
					inputBoxShower(updateN);
					inputBoxShower(updateSnakeSpeed);
					System.out.println(canClick);
					if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)
							&& !Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
						canClick = true;
					}
					if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT)
							|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) && canClick) {
						canClick = false;
						if (backButton.clickedButton()) {
							if (!(updateM.getString().equals(""))) {
								m = (updateM.getNumber() > 100) ? 100
										: (updateM.getNumber() < 5) ? 5 : updateM.getNumber();
							}
							if (!(updateN.getString().equals(""))) {
								n = (updateN.getNumber() > 100) ? 100
										: (updateN.getNumber() < 5) ? 5 : updateN.getNumber();
							}
							if (!(updateSnakeSpeed.getString().equals(""))) {
								System.out.println("hej");
								maxcounter = (updateSnakeSpeed.getNumber() > 15) ? 1
										: (updateSnakeSpeed.getNumber() < 1) ? 30
												: (30 - updateSnakeSpeed.getNumber() * 2);
							}
							System.out.println(n + " " + m);
							System.out.println(maxcounter);
							currentScene = Scene.Main_Scene;
						} else if (controlsSettingsButton.clickedButton()) {
							canClick = false;
							showControlls = true;
						}
					}

				} else {
					ScreenUtils.clear(0, 0, 1, 1);
					camera.update();
					batch.setProjectionMatrix(camera.combined);
					shape.begin(ShapeType.Filled);
					showButton(backButton);
					showButton(setSizesSettingsButton, color);
					showButton(Player1, color);
					showButton(Player2, color);
					showButton(Player3, color);
					shape.end();
					batch.begin();

					batch.draw(PlayerOne, (int) (screenWidth / 8 - screenWidth / 2), (int) (-screenHeight / 6),
							(int) (screenWidth / 4),
							(int) (screenHeight / 4));
					batch.draw(PlayerTwo, (int) (screenWidth / 12 - screenWidth / 5), (int) (-screenHeight / 6),
							(int) (screenWidth / 4),
							(int) (screenHeight / 4));
					batch.draw(PlayerThree, (int) (screenWidth / 7), (int) (-screenHeight / 6), (int) (screenWidth / 4),
							(int) (screenHeight / 4));
					batch.end();
					if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)
							&& !Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
						canClick = true;
					}
					if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT)
							|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) && canClick) {
						canClick = false;
						if (backButton.clickedButton()) {
							currentScene = Scene.Main_Scene;
						} else if (setSizesSettingsButton.clickedButton()) {
							canClick = false;
							showControlls = false;
						}
					}

				}
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
				showButton(settingsButton);
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
					} else if (settingsButton.clickedButton()) {
						currentScene = Scene.Main_Setting;
					}
					for (Button feature : features) {
						if (feature.clickedButton()) {
							if (feature.gethandler().equals(multiplayerHandler2)) {
								multiplayerHandler3.disable();
							}
							if (feature.gethandler().equals(multiplayerHandler3)) {
								multiplayerHandler2.disable();
							}
							feature.toggleisEnabled();
						}
					}
					multiplayerHandler = (multiplayerHandler2.isEnabled()) ? multiplayerHandler2
							: (multiplayerHandler3.isEnabled() ? multiplayerHandler3 : multiplayerHandler);

				}
				shape.end();
				break;
			case Main_Game:
				ScreenUtils.clear(normColor(29, 32, 219, 255));
				camera.update();
				batch.setProjectionMatrix(camera.combined);
				shape.begin(ShapeType.Filled);
				Rectangle[][] shower = grid.show(viewport.getScreenWidth(), viewport.getScreenHeight());

				shape.end();
				batch.begin();
				for (int i = 0; i < grid.snakes.length; i++) {
					font2.setColor(Color.ORANGE);
					font.setColor(Color.ORANGE);
					scoreNumText.setText(font2, "22");
					colonText.setText(font, " : ");
					scoreText.setText(font2, "PLAYER " + (i + 1) + " SCORE");
					float offset = -(grid.gridSize.x * grid.squareSize) / 2 - 20;
					scoreNumText.setText(font2, "" + grid.snakes[i].getScore());
					font2.draw(batch, scoreText, -offset, (0.41f + 0.075f * -i) * viewport.getScreenHeight());
					font.draw(batch, colonText, -offset + scoreText.width,
							(float) (0.41f + 0.075f * -i) * viewport.getScreenHeight());
					font2.draw(batch, scoreNumText, -offset + scoreText.width + colonText.width - 20,
							(0.41f + 0.075f * -i) * viewport.getScreenHeight());
					if (quickTimeHandler.isEnabled()) {
						font2.setColor(Color.RED);
						font.setColor(Color.RED);
						colonText.setText(font, " : ");
						quickTimerText.setText(font2, "PLAYER " + (i + 1) + " TIME");
						scoreNumText.setText(font2, "" + (10 - grid.snakes[i].getQuickTimeCounter() / 30)); // 30 fps
						font2.draw(batch, quickTimerText, i == 2 ? offset + 20 + 250 : offset + 20 + (450 * i),
								i == 2 ? (0.49f + 0.075f * -1) * viewport.getScreenHeight()
										: (0.55f + 0.075f * -1) * viewport.getScreenHeight());
						font.draw(batch, colonText,
								i == 2 ? offset + 20 + 250 + quickTimerText.width
										: offset + 20 + quickTimerText.width + (450 * i),
								i == 2 ? (0.49f + 0.075f * -1) * viewport.getScreenHeight()
										: (0.55f + 0.075f * -1) * viewport.getScreenHeight());
						font2.draw(batch, scoreNumText,
								i == 2 ? offset + 20 + 250 + quickTimerText.width + colonText.width
										: offset + quickTimerText.width + colonText.width + (450 * i),
								i == 2 ? (0.49f + 0.075f * -1) * viewport.getScreenHeight()
										: (0.55f + 0.075f * -1) * viewport.getScreenHeight());
					}
					font2.setColor(Color.ORANGE);
					font.setColor(Color.ORANGE);
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

				for (Fruit fruit : fruits) {
					batch.draw(fruit.getSprite(), (fruit.getSpritePos().x), (fruit.getSpritePos().y), grid.squareSize,
							grid.squareSize);
				}
				batch.end();
				if (allSnakesDead) {
					shape.begin(ShapeType.Filled);
					showButton(restartButton, Color.RED);
					shape.end();

				}
				if (allSnakesDead && (Gdx.input.isButtonPressed(Input.Buttons.LEFT)
						|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT))) {
					if (restartButton.clickedButton()) {
						System.out.println("test");
						allSnakesDead = false;
						currentScene = Scene.Main_Restart;
					}
				}
				showButton(backButton);
				batch.begin();
				if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT)
						|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) && backButton.clickedButton()) {
					currentScene = Scene.Main_Scene;
					if (allSnakesDead) {
						saveScore();
					}
				}
				batch.end();

				if (wallHandler.isEnabled()) {
					drawWalls();
				}
				checkFruitCollsions();

				if (!multiplayerHandler.isEnabled()) {
					leaderboardShower();
				}

				break;
			case Main_Restart:
				ScreenUtils.clear(0, 0, 1, 1);
				camera.update();
				batch.setProjectionMatrix(camera.combined);
				shape.begin(ShapeType.Filled);
				shape.end();
				saveScore();

				gridsize = new Vector(n, m);
				grid = new Grid(gridsize,
						multiplayerHandler.isEnabled() ? multiplayerHandler.getPlayerAmount() : 1,
						(int) screenHeight);
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

	private void drawMainScreen() {
		batch.begin();
		batch.flush();
		snakeText.setText(mainScreenFont, "SNAKE");
		mainScreenFont.draw(batch, snakeText, (int) (-1 * snakeText.width / 2),
				(int) (screenHeight / 2 - snakeText.height / 10));
		Sprite snakeCorner1 = new Sprite(snakeBodyCornerSprite);
		snakeCorner1.flip(true, true);
		Sprite snakeCorner2 = new Sprite(snakeBodyCornerSprite);
		snakeCorner2.flip(false, true);
		Sprite snakeCorner3 = new Sprite(snakeBodyCornerSprite);
		snakeCorner3.flip(true, false);
		Sprite snakeCorner4 = new Sprite(snakeBodyCornerSprite);

		Sprite snakeHead = new Sprite(snakeHeadSidewaysSprite);

		Sprite snakeCoffeeHead = new Sprite(snakeHeadCoffeeSidewaysSprite);

		int headspawnX;
		int fruitX;

		if (snakeReverseHandler.isEnabled()) {
			headspawnX = 0;
			fruitX = -60;
			snakeCoffeeHead.flip(true, false);
			snakeHead.flip(true, false);
		} else {
			headspawnX = -60;
			fruitX = 0;
		}

		// top snake
		for (int i = 0; i < 12; i++) {
			// head and apple
			if (i == 6 || i == 5) {
				continue;
			}
			if (i < 11) {
				batch.draw(snakeBodySidewaysSprite, 300 - i * 60, 200, 60, 60);
			} else {
				batch.draw(snakeCorner4, 300 - i * 60, 200, 60, 60);
			}
		}
		// left snake
		for (int i = 0; i < 10; i++) {
			if (i < 9) {

				batch.draw(snakeBodySprite, -360, 140 - i * 60, 60, 60);
			} else {
				batch.draw(snakeCorner2, -360, 140 - i * 60, 60, 60);
			}
		}
		// bottom snake
		for (int i = 0; i < 11; i++) {
			if (i < 10) {

				batch.draw(snakeBodySidewaysSprite, -300 + 60 * i, -400, 60, 60);
			} else {
				batch.draw(snakeCorner1, -300 + 60 * i, -400, 60, 60);
			}
		}
		// right snake
		for (int i = 0; i < 10; i++) {
			if (i < 9) {

				batch.draw(snakeBodySprite, 300, -340 + i * 60, 60, 60);
			} else {
				batch.draw(snakeCorner3, 300, -340 + i * 60, 60, 60);
			}
		}
		if (wallHandler.isEnabled()) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 9; j++) {
					batch.draw(wallSprite, -300 + 540 * i, 140 - j * 60, 60, 60);
				}
			}
		}
		Sprite snakeHeadSprite1 = snakeHead;

		if (coffeeBeanHandler.isEnabled()) {
			snakeHeadSprite1 = snakeCoffeeHead;
			batch.draw(coffeeBeanSprite, fruitX, 200, 60, 60);

		}
		if (dragonFruitHandler.isEnabled()) {
			effect.start();
			if (snakeReverseHandler.isEnabled()) {
				effect.getEmitters().first().getAngle().setHigh((int) 180 - 10, (int) 180 + 10);
				effect.getEmitters().first().getAngle().setLow((int) 180 - 10, (int) 180 + 10);
				effect.setPosition(headspawnX, 200 + 60 / 2);
			} else {
				effect.getEmitters().first().getAngle().setHigh((int) 0 - 10, (int) 0 + 10);
				effect.getEmitters().first().getAngle().setLow((int) 0 - 10, (int) 0 + 10);
				effect.setPosition(headspawnX + 60, 200 + 60 / 2);
			}
			effect.draw(batch, Gdx.graphics.getDeltaTime());
		} else if (!dragonFruitHandler.isEnabled() && !coffeeBeanHandler.isEnabled()) {
			batch.draw(appleSprite, fruitX, 200, 60, 60);
		}
		batch.draw(snakeHeadSprite1, headspawnX, 200, 60, 60);

		;

		if (effect.isComplete()) {
			effect.reset();
			effect.start();
		}

		// timer
		if (quickTimeHandler.isEnabled()) {
			batch.draw(timerSprite, snakeReverseHandler.isEnabled() ? headspawnX + 22 : headspawnX - 102, 200 - 47, 150,
					150);
		}
		if (multiplayerHandler2.isEnabled()) {
			batch.setColor(Color.CHARTREUSE);
			if (coffeeBeanHandler.isEnabled()) {
				batch.draw(snakeHeadCoffeeSprite, fruitX, 260, 60, 60);
			} else {
				batch.draw(snakeHeadSprite, fruitX, 260, 60, 60);
			}
			batch.draw(snakeBodySprite, fruitX, 320, 60, 60);
		}
		if (multiplayerHandler3.isEnabled()) {

			batch.setColor(snakeReverseHandler.isEnabled() ? Color.RED : Color.CHARTREUSE);
			batch.draw(snakeHeadSidewaysSprite, -60, 260, 60, 60);
			// top left
			for (int i = 0; i < 6; i++) {
				if (i < 5) {
					batch.draw(snakeBodySidewaysSprite, -120 - i * 60, 260, 60, 60);
				} else {
					batch.draw(snakeCorner4, -120 - i * 60, 260, 60, 60);
				}
			}
			// left
			for (int i = 0; i < 12; i++) {
				if (i < 11) {
					batch.draw(snakeBodySprite, -420, 200 - i * 60, 60, 60);
				} else {
					batch.draw(snakeCorner2, -420, 200 - i * 60, 60, 60);
				}
			}
			// bottomleft
			for (int i = 0; i < 6; i++) {
				batch.draw(snakeBodySidewaysSprite, -360 + i * 60, -460, 60, 60);
			}

			batch.setColor(snakeReverseHandler.isEnabled() ? Color.CHARTREUSE : Color.RED);
			if (!snakeReverseHandler.isEnabled()) {
				snakeHead.flip(true, false);
			}
			batch.draw(snakeHead, 0, 260, 60, 60);
			// top right
			for (int i = 0; i < 6; i++) {
				if (i < 5) {
					batch.draw(snakeBodySidewaysSprite, 60 + i * 60, 260, 60, 60);
				} else {
					batch.draw(snakeCorner3, 60 + i * 60, 260, 60, 60);
				}
			}
			// right
			for (int i = 0; i < 12; i++) {
				if (i < 11) {
					batch.draw(snakeBodySprite, 360, 200 - i * 60, 60, 60);
				} else {
					batch.draw(snakeCorner1, 360, 200 - i * 60, 60, 60);
				}
			}
			// bottom right
			for (int i = 0; i < 6; i++) {
				batch.draw(snakeBodySidewaysSprite, 300 - i * 60, -460, 60, 60);
			}

		}

		batch.setColor(Color.WHITE);
		batch.end();
	}

	public Color normColor(float r, float g, float b, float a) {
		return new Color(r / 255, g / 255, b / 255, a / 255);

	}

	public void saveScore() {
		users.updateUser(new User(username, grid.snakes[0].getScore()));
		leaderboard.updateLeaderboard(new Highscore(username, grid.snakes[0].getScore(), getFeatureHash(features)));
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
						snake.setMaxcounter(coffeeBeanHandler.getCoffeeSpeed());
						snake.setSpeedCounter(coffeeBeanHandler.getCoffeDuration());
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
		for (int i = 0; i < grid.snakes.length; i++) {
			Snake snake = grid.snakes[i];
			ArrayList<Vector> positions = snake.getPositions();
			for (int k = 0; k < positions.size(); k++) {
				int cx = positions.get(k).x;
				int cy = positions.get(k).y;
				if (positions.get(k) == positions.get(positions.size() - 1)) {

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
					shape.setColor(Color.BLACK);
					Sprite sprY;
					Sprite sprX;
					if (snake.getSpeedCounter() > 0) { // Is coffee effect active
						sprY = new Sprite(snakeHeadCoffeeSprite);
						sprX = new Sprite(snakeHeadCoffeeSidewaysSprite);

					} else {
						sprY = new Sprite(snakeHeadSprite);
						sprX = new Sprite(snakeHeadSidewaysSprite);
					}

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
								(float) (shower[cx][cy].x - screenWidth / 2 + grid.squareSize / 2
										+ snake.getVel().x * (grid.squareSize / 2)),
								(float) (shower[cx][cy].y - screenHeight / 2 + grid.squareSize / 2
										+ snake.getVel().y * (grid.squareSize / 2)));

						effect.getEmitters().first().getAngle().setHigh((int) snake.getVel().angle() - 10,
								(int) snake.getVel().angle() + 10);
						effect.getEmitters().first().getAngle().setLow((int) snake.getVel().angle() - 10,
								(int) snake.getVel().angle() + 10);
						if (!snake.getVel().equals(snake.getDragonOldVel())) {
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
					// Make snake body sprite in the right direction
					Vector lastPos = (k > 0) ? positions.get(k - 1) : null;
					Vector nextPos = positions.get(k + 1);
					Vector currPos = positions.get(k);
					Vector vel = new Vector(0, 0);
					boolean snakeTeleportLast = false;
					boolean snakeTeleportNext = false;
					if (lastPos != null) {
						vel = currPos.add(lastPos.mult(-1));
						snakeTeleportLast = vel.mag() > 1;
					}
					if (lastPos == null) {
						vel = nextPos.add(currPos.mult(-1));
					}
					boolean isBorderTeleportLast = vel.mag() == 14;
					if (snakeTeleportLast) {
						vel = isBorderTeleportLast ? vel : nextPos.add(currPos.mult(-1));
						vel = new Vector(Math.max(Math.min(vel.x, 1), -1), Math.max(Math.min(vel.y, 1), -1));
						vel = isBorderTeleportLast ? vel.mult(-1) : vel;
					}

					Vector vel2 = nextPos.add(currPos.mult(-1));
					Sprite sprY = new Sprite(snakeBodySprite);
					Sprite sprX = new Sprite(snakeBodySidewaysSprite);

					snakeTeleportNext = vel2.mag() > 1;
					boolean isBorderTeleportNext = vel2.mag() == 14;

					if (snakeTeleportNext && lastPos == null) {
						vel = new Vector(Math.max(Math.min(vel.x, 1), -1), Math.max(Math.min(vel.y, 1), -1));
						vel = isBorderTeleportNext ? vel.mult(-1) : vel;
					}
					if (snakeTeleportNext) {
						vel2 = new Vector(Math.max(Math.min(vel2.x, 1), -1), Math.max(Math.min(vel2.y, 1), -1));
						vel2 = isBorderTeleportNext ? vel2.mult(-1) : vel;
					}

					sprY.setFlip(false, vel.y == -1);
					sprX.setFlip(vel.x == 1, false);
					Sprite spr = vel.x == 0 ? sprY : sprX;
					if (!vel2.equals(vel)) {
						spr = new Sprite(snakeBodyCornerSprite);
						spr.setFlip(vel.x == 1 || vel2.x == -1, vel.y == -1 || vel2.y == 1);
					}
					batch.draw(spr, (int) (shower[cx][cy].x - screenWidth / 2),
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
			batch.setColor(i == 0 ? Color.CHARTREUSE : Color.RED);

			if (snake.isDead) {
				deadSnakeCounter++;
			}
		}
		batch.end();
		if (deadSnakeCounter == grid.snakes.length) {

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

	private void inputBoxShower(InputBox inputBox) {
		inputBox.enable((int) screenHeight);
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

	public BitmapFont createFont(int size) {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Retroville NC.ttf"));
		parameter.size = size;
		font3 = generator.generateFont(parameter);
		font3.setColor(Color.ORANGE);
		return font3;
	}

	public BitmapFont createFont(int size, Color color) {
		font3 = createFont(size);
		font3.setColor(color);
		return font3;
	}

	public void showButton(Button temp) {
		batch.begin();
		batch.draw(temp.getTexture(), temp.getpos().x, temp.getpos().y, temp.getSize().x, temp.getSize().y);
		batch.end();
	}

	public void showButton(Button temp, Color color) {
		shape.setColor(color);
		shape.rect(temp.getpos().x, temp.getpos().y, temp.getSize().x, temp.getSize().y);
		shape.end();
		if (temp.gethandler() != null) {
			batch.begin();
			featureFont.draw(batch, temp.getfeatureName(), temp.getpos().x - (int) (screenWidth / 2),
					temp.getpos().y - (int) (screenHeight / 2) + Math.round(1.75 * temp.getSize().y));
			batch.end();
		} else if (temp.gettext() != null) {
			batch.begin();
			temp.getfont().draw(batch, temp.gettext(),
					temp.getpos().x - (int) (screenWidth / 2) - (getTextSize(temp.getfont(), temp.gettext()).x / 2)
							+ (temp.getSize().x / 2),
					temp.getpos().y - (int) (screenHeight / 2) + (getTextSize(temp.getfont(), temp.gettext()).y / 2)
							+ (temp.getSize().y / 2));
			batch.end();
		}
		shape.begin(ShapeType.Filled);
	}

	public int getFeatureHash(Button[] features) {
		String vals = "";
		for (Button button : features) {
			vals += "" + button.gethandler().isEnabled();
		}
		return Objects.hashCode(vals);
	}

	public Vector getTextSize(BitmapFont font, String text) {
		GlyphLayout glyph = new GlyphLayout();
		glyph.setText(font, text);
		return new Vector((int) glyph.width, (int) glyph.height);
	}

	private void leaderboardShower() {

		int height = (int) screenHeight / 5;

		score.setColor(Color.GOLD);
		batch.begin();
		score.draw(batch, "Leaderboard:", (int) -screenWidth / 2 + 50, height);
		int j = 1;
		for (int i = 0; i < leaderboard.getLeaderboard().length; i++) {
			Highscore curr = leaderboard.getLeaderboard()[i];
			if (curr.getFeatures() == getFeatureHash(features)) {
				System.out.println("abe!");
				score.draw(batch,
						leaderboard.getLeaderboard()[i].getScore() + " by: "
								+ leaderboard.getLeaderboard()[i].getUsername(),
						(int) (-screenWidth / 2) + 50, height - j * 50);
				j++;
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
		font.dispose();
		font2.dispose();
		font3.dispose();
		featureFont.dispose();
		loginFont.dispose();
	}
}
