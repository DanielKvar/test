package com.myambulance.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Artwork from https://goodstuffnononsense.com/about/
 * https://goodstuffnononsense.com/hand-drawn-icons/space-icons/
 */
public class MyAmbulanceGame extends ApplicationAdapter {
	private Texture astronautImage;
	private Texture ambulanceImage;
	private Texture asteroidImage;
	private Sound astronautSound;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle rocket;
	private Array<Rectangle> astronauts; //special LibGDX Array
	private Array<Rectangle> asteroids;
	private long lastAstronautTime;
	private long lastAsteroidTime;
	private int astronoutsRescuedScore;
	private int rocketHealth; //Starts with 100

	private BitmapFont font;

	//Values are set experimental
	private static int SPEED = 600; // pixels per second
	private static int SPEED_ASTRONAUT = 200; // pixels per second
	private static int SPEED_ASTROID = 100; // pixels per second
	private static long CREATE_ASTRONOUT_TIME = 1000000000; //ns
	private static long CREATE_ASTEROID_TIME = 2000000000; //ns

	private void commandMoveLeft() {
		rocket.x -= SPEED * Gdx.graphics.getDeltaTime();
		if(rocket.x < 0) rocket.x = 0;
	}

	private void commandMoveReght() {
		rocket.x += SPEED * Gdx.graphics.getDeltaTime();
		if(rocket.x > Gdx.graphics.getWidth() - ambulanceImage.getWidth())
			rocket.x = Gdx.graphics.getWidth() - ambulanceImage.getWidth();
	}

	private void commandMoveLeftCorner() {
		rocket.x = 0;
	}
	private void commandMoveRightCorner() {
		rocket.x = Gdx.graphics.getWidth() - ambulanceImage.getWidth();
	}

	private void commandTouched() {
		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		rocket.x = touchPos.x - ambulanceImage.getWidth() / 2;
	}

	private void commandExitGame() {
		Gdx.app.exit();
	}

	@Override
	public void create() {

		font = new BitmapFont();
		font.getData().setScale(2);
		astronoutsRescuedScore = 0;
		rocketHealth = 100;

		// default way to load texture
		ambulanceImage = new Texture(Gdx.files.internal("ambulance.png"));
		//astronautImage = new Texture(Gdx.files.internal("astronaut48.png"));
		//asteroidImage = new Texture(Gdx.files.internal("asteroid128.png"));
		//astronautSound = Gdx.audio.newSound(Gdx.files.internal("pick.wav"));

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();

		// create a Rectangle to logically represents the rocket
		rocket = new Rectangle();
		rocket.x = Gdx.graphics.getWidth() / 2 - ambulanceImage.getWidth() / 2; // center the rocket horizontally
		rocket.y = 20; // bottom left corner of the rocket is 20 pixels above the bottom screen edge
		rocket.width = ambulanceImage.getWidth();
		rocket.height = ambulanceImage.getHeight();

		astronauts = new Array<Rectangle>();
		asteroids = new Array<Rectangle>();
		//add first astronoutn and asteroid
		spawnAstronaut();
		spawnAsteroid();

	}

	private void spawnAstronaut() {
		Rectangle astronaut = new Rectangle();
		astronaut.x = MathUtils.random(0, Gdx.graphics.getWidth() - astronautImage.getWidth());
		astronaut.y = Gdx.graphics.getHeight();
		astronaut.width  = astronautImage.getWidth();
		astronaut.height = astronautImage.getHeight();
		astronauts.add(astronaut);
		lastAstronautTime = TimeUtils.nanoTime();
	}

	private void spawnAsteroid() {
		Rectangle asteroid = new Rectangle();
		asteroid.x = MathUtils.random(0, Gdx.graphics.getWidth()- astronautImage.getWidth());
		asteroid.y = Gdx.graphics.getHeight();
		asteroid.width = asteroidImage.getWidth();
		asteroid.height = asteroidImage.getHeight();
		asteroids.add(asteroid);
		lastAsteroidTime = TimeUtils.nanoTime();
	}


	@Override
	public void render() { //runs every frame
		//clear screen
		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the rocket, astronauts, asteroids
		batch.begin();
		{ //add brackets just for intent
			batch.draw(ambulanceImage, rocket.x, rocket.y);
			for (Rectangle asteroid : asteroids) {
				batch.draw(asteroidImage, asteroid.x, asteroid.y);

			}
			for (Rectangle astronaut : astronauts) {
				batch.draw(astronautImage, astronaut.x, astronaut.y);
			}
			font.setColor(Color.YELLOW);
			font.draw(batch, "" + astronoutsRescuedScore, Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 20);
			font.setColor(Color.GREEN);
			font.draw(batch, "" + rocketHealth, 20, Gdx.graphics.getHeight() - 20);
		}
		batch.end();

		// process user input
		if(Gdx.input.isTouched()) commandTouched(); //mouse or touch screen
		if(Gdx.input.isKeyPressed(Keys.LEFT)) commandMoveLeft();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) commandMoveReght();
		if(Gdx.input.isKeyPressed(Keys.A)) commandMoveLeftCorner();
		if(Gdx.input.isKeyPressed(Keys.S)) commandMoveRightCorner();
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) commandExitGame();

		// check if we need to create a new
		if(TimeUtils.nanoTime() - lastAstronautTime > CREATE_ASTRONOUT_TIME) spawnAstronaut();
		if(TimeUtils.nanoTime() - lastAsteroidTime > CREATE_ASTEROID_TIME) spawnAsteroid();

		if (rocketHealth > 0) { //is game end?
			// move and remove any that are beneath the bottom edge of
			// the screen or that hit the rocket.
			for (Iterator<Rectangle> iter = asteroids.iterator(); iter.hasNext(); ) {
				Rectangle asteroid = iter.next();
				asteroid.y -= SPEED_ASTROID * Gdx.graphics.getDeltaTime();
				if (asteroid.y + asteroidImage.getHeight() < 0) iter.remove();
				if (asteroid.overlaps(rocket)) {
					astronautSound.play();
					rocketHealth--;
				}
			}

			for (Iterator<Rectangle> iter = astronauts.iterator(); iter.hasNext(); ) {
				Rectangle astronaut = iter.next();
				astronaut.y -= SPEED_ASTRONAUT * Gdx.graphics.getDeltaTime();
				if (astronaut.y + astronautImage.getHeight() < 0) iter.remove(); //From screen
				if (astronaut.overlaps(rocket)) {
					astronautSound.play();
					astronoutsRescuedScore++;
					if (astronoutsRescuedScore%10==0) SPEED_ASTROID+=66; //speeds up
					iter.remove(); //smart Array enables remove from Array
				}
			}
		} else { //health of rocket is 0 or less
			batch.begin();
			{
				font.setColor(Color.RED);
				font.draw(batch, "The END", Gdx.graphics.getHeight() / 2, Gdx.graphics.getHeight() / 2);
			}
			batch.end();
		}
	}

	@Override
	public void dispose() {
		// dispose of all the native resources
		astronautImage.dispose();
		ambulanceImage.dispose();
		astronautSound.dispose();
		batch.dispose();
		font.dispose();
	}
}