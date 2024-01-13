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
import com.snake.game.handlers.*;
import com.snake.game.util.*;

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
	BitmapFont colonFont;
	BitmapFont scoreFont;
	BitmapFont tempFont;
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
	double widthScaling;
	double heightScaling;
	double scalingFactor;
	int maxcounter = 8;
	int gameStartMaxCounter;

	boolean canClick = true;
	boolean allSnakesDead;
	boolean showControlls;
	int frameCounter = 0;
	InputBox inputBox;
	int boxesHeight,
			boxesWidth;
	Button backButton, startButton, featureButton, controlsSettingsButton, restartButton, settingsButton, settingsRect,
			nTextRect, mTextRect, setSizesSettingsButton, Player1, Player2, Player3, snakeSpeedRect, fruitAmountRect,
			loginButton;
	InputBox updateSnakeSpeed, updateM, updateN, updateFruitAmount;
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
		FreeTypeFontGenerator.setMaxTextureSize(2048);
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
		widthScaling = screenWidth / 1920.0;
		heightScaling = screenHeight / 1080.0;
		scalingFactor = (widthScaling + heightScaling) / 2;


		camera = new OrthographicCamera();
		// camera.setToOrtho(false, 1920, 1080);
		viewport = new FitViewport((int) screenWidth, (int) screenHeight, camera);

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Pixel Sans Serif.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 72;
		colonFont = generator.generateFont(parameter);
		colonFont.setColor(Color.ORANGE);
		generator.dispose();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/snes.ttf"));
		parameter.size = 90;
		scoreFont = generator.generateFont(parameter);
		scoreFont.setColor(Color.ORANGE);
		generator.dispose();
		featureFont = createFont(
				(int) (((double) screenWidth * 4 / 5.0f*3) / (102 * ((double) screenWidth / 1920.0f))));
		mainScreenFont = createFont( (int)(200 * screenWidth / 1920.0), Color.BLACK);

		scoreText = new GlyphLayout();
		scoreText.setText(scoreFont, "Player 1 SCORE");
		quickTimerText = new GlyphLayout();
		quickTimerText.setText(scoreFont, "Player 1 Time");
		colonText = new GlyphLayout();
		colonText.setText(colonFont, ":");
		scoreNumText = new GlyphLayout();
		snakeText = new GlyphLayout();

		inputBox = new InputBox(0, new Vector(100, 100), new Vector(100, 100));
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Pixel Sans Serif.ttf"));

		// Fruits
		apple = new FruitType(appleSprite, 10, multiplayerHandler.isEnabled() ? 5 : 1, 0, cherryHandler);
		goldenApple = new FruitType(goldenAppleSprite, 5, -1, goldenFruitHandler.getChance(), goldenFruitHandler);
		cherry1 = new FruitType(cherry1Sprite, 5, 1, cherryHandler.getChance(), cherryHandler);
		cherry2 = new FruitType(cherry2Sprite, 0, 0, 0, cherryHandler);
		coffeeBean = new FruitType(coffeeBeanSprite, 15, 1, coffeeBeanHandler.getChance(), coffeeBeanHandler);
		dragonFruit = new FruitType(dragonFruitSprite, 5, 0, dragonFruitHandler.getChance(), dragonFruitHandler);

		// making setting scene (Martin)

		settingsRect = new Button(
				new Vector((int) (screenWidth / 2.0 - screenWidth / 4.0), (int) (screenHeight / 2.0 + screenHeight / 8.0)),
				new Vector((int) (screenWidth / 2.0), (int) (screenHeight / 8.0)),
				createFont((int) (72 * scalingFactor)),
				"Gridsize");
		nTextRect = new Button(new Vector((int) (screenWidth / 2.0 - screenWidth / 4.0), (int) (screenHeight / 2.0)),
				new Vector((int) (screenWidth / 8.0), (int) (screenHeight / 8.0)),
				createFont(((int) ((66 *scalingFactor)))),
				"n:");
		mTextRect = new Button(new Vector((int) (screenWidth / 2.0), (int) (screenHeight / 2.0)),
				new Vector((int) (screenWidth / 8.0), (int) (screenHeight / 8.0)),
				createFont(((int) ((66 *scalingFactor)))),
				"m:");

		fruitAmountRect = new Button(
				new Vector((int) (screenWidth / 2 - screenWidth / 4), (int) (screenHeight / 2 - screenHeight * 3 / 7)),
				new Vector((int) (screenWidth / 3), (int) (screenHeight / 6)),
				createFont((int) (42 * scalingFactor)),
				"Fruit Amount:");
		snakeSpeedRect = new Button(
				new Vector((int) (screenWidth / 2 - screenWidth / 4), (int) (screenHeight / 2 - screenHeight * 3 / 14)),
				new Vector((int) (screenWidth / 3), (int) (screenHeight / 6)),
				createFont((int) (45 * scalingFactor )),
				"Snake Speed:");

		updateN = new InputBox(1,
				new Vector((int) (screenWidth / 2 - screenWidth / 7), (int) (screenHeight / 2 + screenHeight / 32)),
				new Vector((int) (screenWidth / 7), (int) (screenHeight / 16)), 3);
		updateM = new InputBox(1,
				new Vector((int) (screenWidth / 2 + screenWidth / 10), (int) (screenHeight / 2 + screenHeight / 32)),
				new Vector((int) (screenWidth / 7), (int) (screenHeight / 16)), 3);
		updateSnakeSpeed = new InputBox(1,
				new Vector((int) (screenWidth / 2 + screenWidth / 24), (int) (screenHeight / 2 - screenHeight / 6)),
				new Vector((int) (screenWidth / 5), (int) (screenHeight / 12)), 2);
		updateFruitAmount = new InputBox(1,
				new Vector((int) (screenWidth / 2 + screenWidth / 24), (int) (screenHeight / 2 - screenHeight * 2 / 5)),
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

		// Making Buttons (Martin)
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

		// Making buttons for features[], with corresponding handlers (Martin)
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
		loginFont = createFont((int) ( 55 * scalingFactor), Color.BLACK);
		loginContinueFont = createFont((int) (31  * scalingFactor), Color.BLACK);
		loginBoxSize = new Vector((int) (screenWidth / 2), (int) (screenHeight / 2));
		loginBoxPos = new Vector((int) (screenWidth / 2 - loginBoxSize.x / 2),
				(int) (screenHeight / 2 - loginBoxSize.y / 2));

		Vector loginInputSize = new Vector(loginBoxSize.x / 2, loginBoxSize.y / 8);
		Vector loginInputPos = new Vector(loginBoxPos.x + loginBoxSize.x / 2 - loginInputSize.x / 2,
				loginBoxPos.y + loginBoxSize.y / 2 - loginInputSize.y);
		loginInput = new InputBox(0, new Vector(loginInputPos.x, loginInputPos.y),
				new Vector(loginInputSize.x, loginInputSize.y), 6);
		Vector loginButtonSize = new Vector(loginInputSize.x / 2, loginInputSize.y);
		loginButton = new Button(
				new Vector(loginInputPos.x + (loginButtonSize.x / 2),
						(loginInputPos.y - loginButtonSize.y - (loginButtonSize.y / 2))),
				loginButtonSize, loginContinueFont, "Continue");

		users = new Users(json);

		score = createFont((int)(40 * scalingFactor));

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

			json.addStringData(leaderboard.forJSON());
			json.addStringData(users.forJSON());
			json.createFile("data/");
			Gdx.app.exit();
		}
		// Scene Management by Martin
		switch (currentScene) {
			// by Martin
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
				showButton(backButton);
				shape.setColor(Color.GOLD);
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
						gameStartMaxCounter = maxcounter;
						currentScene = Scene.Main_Game;
						gridsize = new Vector(n, m);
						grid = new Grid(gridsize,
								multiplayerHandler.isEnabled() ? multiplayerHandler.getPlayerAmount() : 1,
								(int) screenHeight, maxcounter);
						if (wallHandler.isEnabled()) {
							grid.walls = grid.wallGenerator(gridsize);
						}
					}
				}
				if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT)
						|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) && backButton.clickedButton()) {
					currentScene = Scene.Main_Scene;
				}

				break;
			// by Martin
			case Main_Setting:
				if (!showControlls) {
					ScreenUtils.clear(0, 0, 1, 1);
					camera.update();
					batch.setProjectionMatrix(camera.combined);
					shape.begin(ShapeType.Filled);
					showButton(backButton);
					shape.setColor(Color.YELLOW);
					shape.rect((int) (screenWidth / 2 - screenWidth / 4), (int) (screenHeight / 2),
							(int) (screenWidth / 2), (int) (screenHeight / 8));
					shape.setColor(Color.GREEN);
					shape.rect((int) (screenWidth / 2), (int) (screenHeight / 2 - screenHeight * 3 / 14),
							(int) (screenWidth / 4), (int) (screenHeight / 6));
					shape.setColor(Color.RED);
					shape.rect((int) (screenWidth / 2), (int) (screenHeight / 2 - screenHeight * 3 / 7),
							(int) (screenWidth / 4), (int) (screenHeight / 6));
					showButton(settingsRect, Color.YELLOW);
					showButton(nTextRect, Color.YELLOW);
					showButton(mTextRect, Color.YELLOW);
					showButton(snakeSpeedRect, Color.GREEN);
					showButton(controlsSettingsButton, color);
					showButton(fruitAmountRect, color);
					shape.end();
					inputBoxShower(updateM);
					inputBoxShower(updateN);
					inputBoxShower(updateSnakeSpeed);
					inputBoxShower(updateFruitAmount);
					if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)
							&& !Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
						canClick = true;
					}
					if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT)
							|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) && canClick) {
						canClick = false;
						if (backButton.clickedButton()) {
							if (!(updateM.getString().isEmpty())) {
								m = (updateM.getNumber() > 100) ? 100
										: Math.max(updateM.getNumber(), 5);
							}
							if (!(updateN.getString().isEmpty())) {
								n = (updateN.getNumber() > 100) ? 100
										: Math.max(updateN.getNumber(), 5);
							}
							if (!(updateSnakeSpeed.getString().isEmpty())) {
								maxcounter = Math.min(12, updateSnakeSpeed.getNumber());
								maxcounter = 30 - maxcounter * 2;
							}
							if (!(updateFruitAmount.getString().isEmpty())) {
								fruitAmount = (updateFruitAmount.getNumber() > 15) ? 15
										: Math.max(updateFruitAmount.getNumber(), 1);
							}

							currentScene = Scene.Main_Scene;
						} else if (controlsSettingsButton.clickedButton()) {
							if (!(updateM.getString().isEmpty())) {
								m = (updateM.getNumber() > 100) ? 100
										: Math.max(updateM.getNumber(), 5);
							}
							if (!(updateN.getString().isEmpty())) {
								n = (updateN.getNumber() > 100) ? 100
										: Math.max(updateN.getNumber(), 5);
							}
							if (!(updateSnakeSpeed.getString().isEmpty())) {
								maxcounter = Math.min(12, updateSnakeSpeed.getNumber());
								maxcounter = 30 - maxcounter * 2;
							}
							if (!(updateFruitAmount.getString().isEmpty())) {
								fruitAmount = (updateFruitAmount.getNumber() > 15) ? 1
										: (updateFruitAmount.getNumber() < 1) ? 30
												: updateFruitAmount.getNumber();
							}
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
			// by Martin
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
				drawTextOnScreen();

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

				if (wallHandler.isEnabled()) {
					drawWalls();
				}

				if (allSnakesDead) {
					shape.begin(ShapeType.Filled);
					showButton(restartButton, Color.RED);
					shape.end();

				}
				if (allSnakesDead && (Gdx.input.isButtonPressed(Input.Buttons.LEFT)
						|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT))) {
					if (restartButton.clickedButton()) {
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
						allSnakesDead = false;
					}
				}
				batch.end();

				checkFruitCollsions();

				if (!multiplayerHandler.isEnabled()) {
					leaderboardShower();
				}

				break;
			// by Martin
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
						(int) screenHeight, maxcounter);
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

	// Score text made by Oscar, Quicktime text made by Oliver
	private void drawTextOnScreen() {
		// score text
		for (int i = 0; i < grid.snakes.length; i++) {
			scoreFont.setColor(Color.ORANGE);
			colonFont.setColor(Color.ORANGE);
			scoreNumText.setText(scoreFont, "22");
			colonText.setText(colonFont, " : ");
			scoreText.setText(scoreFont, "PLAYER " + (i + 1) + " SCORE");
			float offset = -(grid.gridSize.x * grid.squareSize) / 2 - 20;
			scoreNumText.setText(scoreFont, "" + grid.snakes[i].getScore());
			scoreFont.draw(batch, scoreText, -offset, (0.41f + 0.075f * -i) * viewport.getScreenHeight());
			colonFont.draw(batch, colonText, -offset + scoreText.width,
					(float) (0.41f + 0.075f * -i) * viewport.getScreenHeight());
			scoreFont.draw(batch, scoreNumText, -offset + scoreText.width + colonText.width - 20,
					(0.41f + 0.075f * -i) * viewport.getScreenHeight());

			// quicktime text
			if (quickTimeHandler.isEnabled()) {
				scoreFont.setColor(Color.RED);
				colonFont.setColor(Color.RED);
				colonText.setText(colonFont, " : ");
				quickTimerText.setText(scoreFont, "PLAYER " + (i + 1) + " TIME");
				scoreNumText.setText(scoreFont, "" + (10 - grid.snakes[i].getQuickTimeCounter() / 30)); // 30 fps
				scoreFont.draw(batch, quickTimerText, i == 2 ? offset + 20 + 250 : offset + 20 + (450 * i),
						i == 2 ? (0.49f + 0.075f * -1) * viewport.getScreenHeight()
								: (0.55f + 0.075f * -1) * viewport.getScreenHeight());
				colonFont.draw(batch, colonText,
						i == 2 ? offset + 20 + 250 + quickTimerText.width
								: offset + 20 + quickTimerText.width + (450 * i),
						i == 2 ? (0.49f + 0.075f * -1) * viewport.getScreenHeight()
								: (0.55f + 0.075f * -1) * viewport.getScreenHeight());
				scoreFont.draw(batch, scoreNumText,
						i == 2 ? offset + 20 + 250 + quickTimerText.width + colonText.width
								: offset + quickTimerText.width + colonText.width + (450 * i),
						i == 2 ? (0.49f + 0.075f * -1) * viewport.getScreenHeight()
								: (0.55f + 0.075f * -1) * viewport.getScreenHeight());
			}
			scoreFont.setColor(Color.ORANGE);
			colonFont.setColor(Color.ORANGE);
		}
		batch.end();
	}

	// made by Oliver, dragonfruit fire made by Oscar
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
		int funSize = (int) (startButton.getSize().x / 8);
		if (snakeReverseHandler.isEnabled()) {
			headspawnX = 0;
			fruitX = -funSize;
			snakeCoffeeHead.flip(true, false);
			snakeHead.flip(true, false);
		} else {
			headspawnX = -funSize;
			fruitX = 0;
		}

		int bodPos = 5 * funSize;
		int snakeBodOffsetYTop = (int) (startButton.getpos().y + (startButton.getSize().y) - (screenHeight / 2));
		int snakeBodOffsetYBot = (int) (snakeBodOffsetYTop - funSize * 9);

		// top snake
		for (int i = 0; i < 12; i++) {
			// head and apple
			if (i == 6 || i == 5) {
				continue;
			}
			if (i < 11) {
				batch.draw(snakeBodySidewaysSprite, bodPos - i * funSize, snakeBodOffsetYTop + funSize, funSize,
						funSize);
			} else {
				batch.draw(snakeCorner4, bodPos - i * funSize, snakeBodOffsetYTop + funSize, funSize, funSize);
			}
		}
		// left snake
		for (int i = 0; i < 10; i++) {
			if (i < 9) {

				batch.draw(snakeBodySprite, bodPos - 11 * funSize, (snakeBodOffsetYTop) - i * funSize, funSize,
						funSize);
			} else {
				batch.draw(snakeCorner2, bodPos - 11 * funSize, (snakeBodOffsetYTop) - i * funSize, funSize, funSize);
			}
		}
		// bottom snake
		for (int i = 0; i < 11; i++) {
			if (i < 10) {

				batch.draw(snakeBodySidewaysSprite, -bodPos + funSize * i, snakeBodOffsetYBot, funSize, funSize);
			} else {
				batch.draw(snakeCorner1, -bodPos + funSize * i, snakeBodOffsetYBot, funSize, funSize);
			}
		}
		// right snake
		for (int i = 0; i < 10; i++) {
			if (i < 9) {

				batch.draw(snakeBodySprite, bodPos, (snakeBodOffsetYBot + funSize) + i * funSize, funSize, funSize);
			} else {
				batch.draw(snakeCorner3, bodPos, (snakeBodOffsetYBot + funSize) + i * funSize, funSize, funSize);
			}
		}
		if (wallHandler.isEnabled()) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 9; j++) {
					batch.draw(wallSprite, -bodPos + 9 * funSize * i, (snakeBodOffsetYTop) - j * funSize, funSize,
							funSize);
				}
			}
		}
		Sprite snakeHeadSprite1 = snakeHead;

		if (coffeeBeanHandler.isEnabled()) {
			snakeHeadSprite1 = snakeCoffeeHead;
			batch.draw(coffeeBeanSprite, fruitX, snakeBodOffsetYTop + funSize, funSize, funSize);

		}
		if (dragonFruitHandler.isEnabled()) {
			effect.start();
			effect = scaleEffect(effect, funSize);
			if (snakeReverseHandler.isEnabled()) {
				effect.getEmitters().first().getAngle().setHigh((int) 180 - 10, (int) 180 + 10);
				effect.getEmitters().first().getAngle().setLow((int) 180 - 10, (int) 180 + 10);
				effect.setPosition(headspawnX, snakeBodOffsetYTop + funSize / 2 + funSize);
			} else {
				effect.getEmitters().first().getAngle().setHigh((int) 0 - 10, (int) 0 + 10);
				effect.getEmitters().first().getAngle().setLow((int) 0 - 10, (int) 0 + 10);
				effect.setPosition(headspawnX + funSize, snakeBodOffsetYTop + funSize / 2 + funSize);
			}
			effect.draw(batch, Gdx.graphics.getDeltaTime());

			if (effect.isComplete()) {
				effect.reset();
				effect.start();
			}
		} else if (!dragonFruitHandler.isEnabled() && !coffeeBeanHandler.isEnabled()) {
			batch.draw(appleSprite, fruitX, snakeBodOffsetYTop + funSize, funSize, funSize);
		}
		batch.draw(snakeHeadSprite1, headspawnX, snakeBodOffsetYTop + funSize, funSize, funSize);

		// timer
		if (quickTimeHandler.isEnabled()) {
			batch.draw(timerSprite, snakeReverseHandler.isEnabled() ? headspawnX + funSize : headspawnX - funSize,
					snakeBodOffsetYTop + funSize + (funSize - (int) (funSize * 0.8)) / 2, (int) (funSize * 0.8),
					(int) (funSize * 0.8));
		}
		// 2 snakes
		if (multiplayerHandler2.isEnabled()) {
			batch.setColor(Color.CHARTREUSE);
			if (coffeeBeanHandler.isEnabled()) {
				batch.draw(snakeHeadCoffeeSprite, fruitX, 2 * funSize + snakeBodOffsetYTop, funSize, funSize);
			} else {
				batch.draw(snakeHeadSprite, fruitX, 2 * funSize + snakeBodOffsetYTop, funSize, funSize);
			}
			batch.draw(snakeBodySprite, fruitX, 3 * funSize + snakeBodOffsetYTop, funSize, funSize);
		}
		// 3 snakes
		if (multiplayerHandler3.isEnabled()) {

			batch.setColor(snakeReverseHandler.isEnabled() ? Color.RED : Color.CHARTREUSE);

			batch.draw(snakeHeadSidewaysSprite, -funSize, 2 * funSize + snakeBodOffsetYTop, funSize, funSize);
			// top left
			for (int i = 0; i < 6; i++) {
				if (i < 5) {
					batch.draw(snakeBodySidewaysSprite, -2 * funSize - i * funSize, snakeBodOffsetYTop + 2 * funSize,
							funSize, funSize);
				} else {
					batch.draw(snakeCorner4, -2 * funSize - i * funSize, snakeBodOffsetYTop + 2 * funSize, funSize,
							funSize);
				}
			}
			// left
			for (int i = 0; i < 12; i++) {
				if (i < 11) {

					batch.draw(snakeBodySprite, -bodPos - 2 * funSize, funSize + snakeBodOffsetYTop - i * funSize,
							funSize, funSize);
				} else {
					batch.draw(snakeCorner2, -bodPos - 2 * funSize, funSize + snakeBodOffsetYTop - i * funSize, funSize,
							funSize);
				}
			}
			// bottomleft
			for (int i = 0; i < 6; i++) {

				batch.draw(snakeBodySidewaysSprite, -bodPos - funSize + i * funSize, snakeBodOffsetYBot - funSize,
						funSize, funSize);
			}

			batch.setColor(snakeReverseHandler.isEnabled() ? Color.CHARTREUSE : Color.RED);
			if (!snakeReverseHandler.isEnabled()) {
				snakeHead.flip(true, false);
			}
			batch.draw(snakeHead, 0, snakeBodOffsetYTop + funSize * 2, funSize, funSize);
			// top right
			for (int i = 0; i < 6; i++) {
				if (i < 5) {
					batch.draw(snakeBodySidewaysSprite, funSize + i * funSize, snakeBodOffsetYTop + funSize * 2,
							funSize, funSize);
				} else {
					batch.draw(snakeCorner3, funSize + i * funSize, snakeBodOffsetYTop + funSize * 2, funSize, funSize);
				}
			}
			// right
			for (int i = 0; i < 12; i++) {
				if (i < 11) {
					batch.draw(snakeBodySprite, bodPos + funSize, funSize + snakeBodOffsetYTop - i * funSize, funSize,
							funSize);
				} else {
					batch.draw(snakeCorner1, bodPos + funSize, funSize + snakeBodOffsetYTop - i * funSize, funSize,
							funSize);
				}
			}
			// bottom right
			for (int i = 0; i < 6; i++) {
				batch.draw(snakeBodySidewaysSprite, bodPos - i * funSize, snakeBodOffsetYBot - funSize, funSize,
						funSize);
			}

		}

		batch.setColor(Color.WHITE);
		batch.end();
	}

	// Made by Oscar
	// Takes standard/normal RGBA values and converts them to a Color object
	public Color normColor(float r, float g, float b, float a) {
		return new Color(r / 255, g / 255, b / 255, a / 255);

	}

	// Made by Oscar
	// Saves the user, and add the score to the leaderboard
	public void saveScore() {
		users.updateUser(new User(username, grid.snakes[0].getScore()));
		leaderboard.updateLeaderboard(new Highscore(username, grid.snakes[0].getScore(), getFeatureHash(features)));
	}

	// Made by Oliver
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
						caffeinate(snake);
					}
					snake.setHasEaten(fruit);
					fruitIterator.remove();
				}
			}
		}

	}

	//Made by Oliver
	//Speeds up snake
	private void caffeinate(Snake snake) {
		snake.setMaxcounter(coffeeBeanHandler.getCoffeeSpeed());
		snake.setSpeedCounter(coffeeBeanHandler.getCoffeDuration());
	}

	// Made by Oliver
	//Cherry logic
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

	// Primarily made by Oscar
	// Reverses all the snakes positions, the head becomes the tail and vice versa
	private static void reverseSnake(Snake snake) {
		ArrayList<Vector> positions = snake.getPositions();
		Collections.reverse(positions);
		snake.setPositions(positions);
		Vector head = positions.get(positions.size() - 1);
		Vector second = positions.get(positions.size() - 2);
		Vector newVel = new Vector(Math.max(Math.min(head.x - second.x, 1), -1),
				Math.max(Math.min(head.y - second.y, 1), -1));

		// Makes sure the snake velosity is now matching the new direction
		if (!newVel.equals(snake.getVel())) {
			snake.setVel(newVel);
			// Sets the last input key corresponding to the new direction
			snake.setKey(snake.keyVectorMapReversed.get(newVel));
		}
	}

	// Primarily made by Oscar
	private void snakeUpdater(Rectangle[][] shower) {
		int deadSnakeCounter = 0;
		for (int i = 0; i < grid.snakes.length; i++) {
			Snake snake = grid.snakes[i];
			ArrayList<Vector> positions = snake.getPositions();
			for (int k = 0; k < positions.size(); k++) {
				int cx = positions.get(k).x;
				int cy = positions.get(k).y;
				if (positions.get(k) == positions.get(positions.size() - 1)) {
					// Boundary collision af Christian
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
					} // End af Christians
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

					// Made by Oscar
					// Draw the snake head facing the right direction
					// Also draws and rotates the fire effect if the dragonfruit is active
					Vector vel = snake.getVel();
					sprY.setFlip(false, vel.y == 1);
					sprX.setFlip(vel.x == -1, false);
					Sprite spr = vel.x == 0 ? sprY : sprX;

					batch.draw(spr, (int) (shower[cx][cy].x - screenWidth / 2),
							(int) (shower[cx][cy].y - screenHeight / 2), grid.squareSize, grid.squareSize);
					if (snake.fireActive) {

						ParticleEffect effect = snake.getEffect();
						if (snake.getFireCounter() == 0) {
							effect = scaleEffect(effect, grid.squareSize);
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
							effect = scaleEffect(effect, grid.squareSize);
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
					// Made by Oscar
					// Draw the snake body facing the right direction
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
					// Check if snake is teleporting through border or other means (cherry)
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

					// Draw snake body corner sprite if snake is turning
					if (!vel2.equals(vel)) {
						spr = new Sprite(snakeBodyCornerSprite);
						spr.setFlip(vel.x == 1 || vel2.x == -1, vel.y == -1 || vel2.y == 1);
					}
					batch.draw(spr, (int) (shower[cx][cy].x - screenWidth / 2),
							(int) (shower[cx][cy].y - screenHeight / 2), grid.squareSize, grid.squareSize);

				}
			}
			// Made by Oscar
			// Quicktime Feature
			// if enabled, kills the snake if it doesn't change direction within the time
			// limit
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

	// Made by Oliver
	// Spawn a random fruit in a random place on screen, that isnt occupied
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

	// Made by Oliver
	private void createFruit(FruitType fruitType, Vector spawningPosition, Rectangle rectangle) {
		fruits.add(new Fruit(spawningPosition, fruitType.getSprite(), new Vector(
				(int) ((rectangle.x - (Gdx.graphics.getWidth() / 2)) + grid.squareSize * spawningPosition.x),
				(int) ((rectangle.y - (Gdx.graphics.getHeight() / 2)) + grid.squareSize * spawningPosition.y)),
				fruitType.getScore(), fruitType.getGrowth()));
	}

	// Christian
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

	// Made by Oscar
	// Scales the particle effect to a given size
	public ParticleEffect scaleEffect(ParticleEffect effect, int size) {
		effect.getEmitters().first().getXScale().setHigh((int) (size / 1.4));
		effect.getEmitters().first().getYScale().setLow((int) (size / 1.4));
		effect.getEmitters().first().getLife().setHigh((int) ((size / 50.0) * 325));
		return effect;
	}

	// Christian
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

	// By Martin
	public BitmapFont createFont(int size) {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Pixel Sans Serif.ttf"));
		parameter.size = size;
		tempFont = generator.generateFont(parameter);
		tempFont.setColor(Color.ORANGE);
		return tempFont;
	}

	// Made by Oscar
	public BitmapFont createFont(int size, Color color) {
		tempFont = createFont(size);
		tempFont.setColor(color);
		return tempFont;
	}

	// by Martin
	public void showButton(Button temp) {
		batch.begin();
		batch.draw(temp.getTexture(), temp.getpos().x, temp.getpos().y, temp.getSize().x, temp.getSize().y);
		batch.end();
	}

	// by Martin
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

	// Made by Oscar
	// Calculates the hash of the current features enabled and settings
	// This is used to compare highscores
	public int getFeatureHash(Button[] features) {
		String vals = "";
		for (Button button : features) {
			vals += "" + button.gethandler().isEnabled();
		}
		vals += gameStartMaxCounter;
		vals += grid.gridSize.x;
		vals += grid.gridSize.y;
		vals += fruitAmount;

		return Objects.hashCode(vals);
	}

	// Made by Oscar
	// Returns the size/dimensions of a given text in a given font
	public Vector getTextSize(BitmapFont font, String text) {
		GlyphLayout glyph = new GlyphLayout();
		glyph.setText(font, text);
		return new Vector((int) glyph.width, (int) glyph.height);
	}

	// Christian
	private void leaderboardShower() {
		int height = (int) screenHeight / 5;
		score.setColor(Color.GOLD);
		batch.begin();
		score.draw(batch, "Leaderboard:", (int) -screenWidth / 2 + 50, height);
		int j = 1;
		for (int i = 0; i < leaderboard.getLeaderboard().length; i++) {
			Highscore curr = leaderboard.getLeaderboard()[i];
			if (curr.getFeatures() == getFeatureHash(features)) {
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
		colonFont.dispose();
		scoreFont.dispose();
		tempFont.dispose();
		featureFont.dispose();
		loginFont.dispose();
	}
}
