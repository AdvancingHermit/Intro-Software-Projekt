package com.snake.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SnakeProjekt extends ApplicationAdapter {

	enum Scene {
		Main_Scene, Main_Game
	}

	Scene currentSceen = Scene.Main_Scene;

	private int n = 10;
	private int m = 10;
	private Vector gridsize;



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
	Texture wallSprite;
	Texture snakeBodySprite;
	Texture snakeHeadSprite;
	Texture snakeHeadSidewaysSprite;

	List<Fruit> fruits = new ArrayList<>();
	List<Fruit> snakePieces = new ArrayList<>();
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
	InputBox inputBox = new InputBox();


	@Override
	public void create() {

		
		batch = new SpriteBatch();

		appleSprite = new Texture((Gdx.files.internal("Apple.png")));
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
		inputBox = new InputBox();
		

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render() {
		inputBox.update();
		System.out.println(inputBox.getString());

		switch (currentSceen) {
			case Main_Scene:
				ScreenUtils.clear(0, 0, 1, 1);
				startButtonX = screenWidth / 2 - 100;
				startButtonY = screenHeight / 2 - 100;
				frameCounter++;
				mousePressed = false;

				if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
					Gdx.app.exit();
				}
				camera.update();
				batch.setProjectionMatrix(camera.combined);
				shape.begin(ShapeType.Filled);
				shape.setColor(Color.WHITE);
				shape.rect(startButtonX, startButtonY, 200, 200); // creating start game
				shape.setColor(Color.RED);
				shape.rect(startButtonX + 400, startButtonY, 100, 200); // creating minus n
				shape.setColor(Color.GREEN);
				shape.rect(startButtonX + 500, startButtonY, 100, 200); // creating plus n
				shape.setColor(Color.RED);
				shape.rect(startButtonX - 400, startButtonY, 100, 200); // creating minus m
				shape.setColor(Color.GREEN);
				shape.rect(startButtonX - 300, startButtonY, 100, 200); // creating plus m
				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
					mousePressed = true;
				}
				if (frameCounter % 5 == 0) {
					if (Gdx.input.getX() >= startButtonX + 400 // Creating start minus n hitbox
							&& Gdx.input.getX() <= startButtonX + 500
							&& Gdx.input.getY() <= startButtonY + 200
							&& Gdx.input.getY() >= startButtonY
							&& mousePressed) {
						n = Math.max(n-1, 5);
						System.out.println("n--: " + n);
						mousePressed = false;
					} else if (Gdx.input.getX() >= startButtonX + 500 // Creating start plus n hitbox
							&& Gdx.input.getX() <= startButtonX + 600
							&& Gdx.input.getY() <= startButtonY + 200
							&& Gdx.input.getY() >= startButtonY
							&& mousePressed) {
					n = Math.min(n+1, 100);
						System.out.println("n++: " + n);
						mousePressed = false;
					}
					if (Gdx.input.getX() >= startButtonX - 400// Creating start minus m hitbox
							&& Gdx.input.getX() <= startButtonX - 300
							&& Gdx.input.getY() <= startButtonY + 200
							&& Gdx.input.getY() >= startButtonY
							&& mousePressed) {
						m = Math.max(m-1, 5);
						System.out.println("m-- " + m);
						mousePressed = false;
					}  else if (Gdx.input.getX() >= startButtonX - 300 // Creating start plus n hitbox
							&& Gdx.input.getX() <= startButtonX - 200
							&& Gdx.input.getY() <= startButtonY + 200
							&& Gdx.input.getY() >= startButtonY
							&& mousePressed) {
						m = Math.min(m+1, 100);
						System.out.println("m++: " + m);
						mousePressed = false;
					} 
				}
				if (Gdx.input.getX() >= startButtonX // Creating start game button hitbox
						&& Gdx.input.getX() <= startButtonX + 200
						&& Gdx.input.getY() <= startButtonY + 200
						&& Gdx.input.getY() >= startButtonY
						&& (Gdx.input.isButtonPressed(Input.Buttons.LEFT)
						|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT))) {
					gridsize = new Vector(n, m);
					grid = new Grid(gridsize, snakeAmount, screenHeight);
          grid.walls = grid.wallGenerator(gridsize);
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
				shape.end();
				batch.begin();

				scoreNumText.setText(font2, "22");
				float offset = (scoreNumText.width + scoreText.width + colonText.width) / 2;
				scoreNumText.setText(font2, "" + grid.snakes[0].getScore());

				font2.draw(batch, scoreText, -offset, 0.41f * viewport.getScreenHeight());
				font.draw(batch, colonText, -offset + scoreText.width, 0.41f * viewport.getScreenHeight());
				font2.draw(batch, scoreNumText, -offset + scoreText.width + colonText.width,
						0.41f * viewport.getScreenHeight());

				batch.end();
				shape.begin(ShapeType.Filled);



				for (Rectangle[] rectangles : shower) {
					for (Rectangle rectangle : rectangles) {
						shape.setColor(Color.WHITE);
						shape.rect(rectangle.x, rectangle.y, grid.squareSize, grid.squareSize);

						while (fruits.isEmpty()) {

							boolean snakeCoversFullScreen = false;
							boolean spawnInSnake = false;
							int randx = random.nextInt(0, gridsize.x);
							int randy = random.nextInt(0, gridsize.y);
							for (Snake snake : grid.snakes) {
								for (Vector pos : snake.getPositions()) {
									if (snake.getPositions().size() >= gridsize.x * gridsize.y) {
										snakeCoversFullScreen = true;
									}
									if (new Vector(randx, randy).equals(pos)) {
										spawnInSnake = true;
									}
								}
							}
							if (snakeCoversFullScreen) {
								break;
							}
							if (!spawnInSnake) {
								fruits.add(new Fruit(new Vector((int) (randx), (int) (randy)), appleSprite, new Vector(
										(int) ((rectangle.x - (Gdx.graphics.getWidth() / 2)) + grid.squareSize * randx),
										(int) ((rectangle.y - (Gdx.graphics.getHeight() / 2))
												+ grid.squareSize * randy))));
							}

						}
					}

				}

				
				for (int k = 0; k < positions.size(); k++) {
					int cx = positions.get(k).x;
					int cy = positions.get(k).y;

					if (cx == grid.gridSize.x || cx == -1) {
						// Skiftes til i, når vi looper over slanger.
						cx = grid.snakes[0].getPositions().get(k).x = grid.gridSize.x - Math.abs(cx);
						grid.snakes[0].move();
					}
					if (cy == grid.gridSize.y || cy == -1) {
						// Skiftes til i, når vi looper over slanger.
						cy = grid.snakes[0].getPositions().get(k).y = grid.gridSize.y - Math.abs(cy);
						grid.snakes[0].move();
					}

					snakePieces.add(new Fruit(new Vector((int) (cx), (int) (cy)), null, new Vector(
						(int) (shower[cx][cy].x - screenWidth / 2),
						(int) (shower[cx][cy].y - screenHeight / 2))));
				}
				shape.end();

				batch.begin();
				for (Fruit fruit : fruits) {
					batch.draw(fruit.getSprite(), (fruit.getSpritePos().x), (fruit.getSpritePos().y), grid.squareSize,
							grid.squareSize);
				}
				for (int i = 0; i < snakePieces.size(); i++) {
					Fruit snakePiece = snakePieces.get(i);

					if (i == positions.size() - 1) {
						shape.setColor(Color.BLACK);
						Sprite sprY = new Sprite(snakeHeadSprite);
						Sprite sprX = new Sprite(snakeHeadSidewaysSprite);
						Vector vel = grid.snakes[0].getVel();
						sprY.setFlip(false,  vel.y == 1);
						sprX.setFlip(vel.x == -1,  false);
						Sprite spr = vel.x == 0 ? sprY : sprX;
						batch.draw(spr, (snakePiece.getSpritePos().x), (snakePiece.getSpritePos().y), grid.squareSize, grid.squareSize);
										 


					} else {
						shape.setColor(Color.GREEN);
						batch.draw(snakeBodySprite, (snakePiece.getSpritePos().x), (snakePiece.getSpritePos().y), grid.squareSize ,grid.squareSize);
					}
					
				}
				snakePieces.clear();
					batch.end();

					batch.begin();

					for(int i = 0; i < grid.walls.length; i++){
						for(int j = 0; j < grid.walls[i].size.x; j++){
							batch.draw(wallSprite, (grid.walls[i].getSpritePos().x) + j * grid.squareSize, (grid.walls[i].getSpritePos().y), grid.squareSize, grid.squareSize);
							for(Snake snake : grid.snakes){
								if(snake.checkCollision(grid.walls[i].getSnakePos().add(new Vector(j, 0)))) {
									snake.isDead = true;
								}
							}
						}
						for(int j = 0; j < grid.walls[i].size.y; j++){
							batch.draw(wallSprite, (grid.walls[i].getSpritePos().x), (grid.walls[i].getSpritePos().y) + j * grid.squareSize, grid.squareSize, grid.squareSize);
							for(Snake snake : grid.snakes){
								if(snake.checkCollision(grid.walls[i].getSnakePos().add(new Vector(0, j)))) {
									snake.isDead = true;
								}
							}
						}
						
					}
				batch.end();
				Iterator<Fruit> fruitIterator = fruits.iterator();
				while (fruitIterator.hasNext()) {
					Fruit fruit = fruitIterator.next();
					for (Snake snake : grid.snakes) {
						if (snake.checkCollision(fruit.getSnakePos())) {
							snake.setHasEaten();
							fruits.remove(fruit);

						}
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
