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
	private Texture syringeImage;
	private Texture ambulanceImage;
	private Texture treeImage;
	private Texture healImage;
	private Sound astronautSound;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle ambulance;
	private Array<Rectangle> syringes; //special LibGDX Array
	private Array<Rectangle> trees;
	private Array<Rectangle> heals;
	private long lastSyringeTime;
	private long lastTreeTime;
	private long lastHealTime;
	private int vaccineScore;
	private int ambulanceHealth; //Starts with 100

	private BitmapFont font;

	//Values are set experimental
	private static int SPEED = 400; // pixels per second
	private static int SPEED_SYRINGE = 150; // pixels per second
	private static int SPEED_TREE = 150; // pixels per second
	private static int SPEED_HEAL = 150; // pixels per second
	private static long CREATE_SYRINGE_TIME = 1500000000; //ns
	private static long CREATE_TREE_TIME = 1000000000; //ns
	private static long CREATE_HEAL_TIME = 10000000000L; //ns

	private void commandMoveLeft() {
		ambulance.x -= SPEED * Gdx.graphics.getDeltaTime();
		if(ambulance.x < 0) ambulance.x = 0;
	}

	private void commandMoveRight() {
		ambulance.x += SPEED * Gdx.graphics.getDeltaTime();
		if(ambulance.x > Gdx.graphics.getWidth() - ambulanceImage.getWidth())
			ambulance.x = Gdx.graphics.getWidth() - ambulanceImage.getWidth();
	}

	private void commandMoveDown() {
		ambulance.y -= SPEED * Gdx.graphics.getDeltaTime();
		if(ambulance.y < 0) ambulance.y = 0;
	}

	private void commandMoveUp() {
		ambulance.y += SPEED * Gdx.graphics.getDeltaTime();
		if(ambulance.y > Gdx.graphics.getHeight() - ambulanceImage.getHeight())
			ambulance.y = Gdx.graphics.getHeight() - ambulanceImage.getHeight();
	}

	private void commandMoveLeftCorner() {
		ambulance.x = 0;
	}
	private void commandMoveRightCorner() {
		ambulance.x = Gdx.graphics.getWidth() - ambulanceImage.getWidth();
	}

	private void commandTouched() {
		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		ambulance.x = touchPos.x - ambulanceImage.getWidth() / 2;
	}

	private void commandExitGame() {
		Gdx.app.exit();
	}

	@Override
	public void create() {

		font = new BitmapFont();
		font.getData().setScale(2);
		vaccineScore = 0;
		ambulanceHealth = 100;

		// default way to load texture
		ambulanceImage = new Texture(Gdx.files.internal("ambulance.png"));
		syringeImage = new Texture(Gdx.files.internal("syringe.png"));
		treeImage = new Texture(Gdx.files.internal("tree.png"));
		healImage = new Texture(Gdx.files.internal("heal.png"));
		//astronautSound = Gdx.audio.newSound(Gdx.files.internal("pick.wav"));

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();

		// create a Rectangle to logically represents the rocket
		ambulance = new Rectangle();
		ambulance.x = Gdx.graphics.getWidth() / 2 - ambulanceImage.getWidth() / 2; // center the rocket horizontally
		ambulance.y = 20; // bottom left corner of the rocket is 20 pixels above the bottom screen edge
		ambulance.width = ambulanceImage.getWidth();
		ambulance.height = ambulanceImage.getHeight();

		syringes = new Array<Rectangle>();
		trees = new Array<Rectangle>();
		heals = new Array<Rectangle>();
		//add first astronoutn and asteroid
		spawnSyringe();
		spawnTree();
		spawnHeal();

	}

	private void spawnSyringe() {
		Rectangle astronaut = new Rectangle();
		astronaut.x = MathUtils.random(0, Gdx.graphics.getWidth() - syringeImage.getWidth());
		astronaut.y = Gdx.graphics.getHeight();
		astronaut.width  = syringeImage.getWidth();
		astronaut.height = syringeImage.getHeight();
		syringes.add(astronaut);
		lastSyringeTime = TimeUtils.nanoTime();
	}

	private void spawnTree() {
		Rectangle tree = new Rectangle();
		tree.x = MathUtils.random(0, Gdx.graphics.getWidth()- treeImage.getWidth());
		tree.y = Gdx.graphics.getHeight();
		tree.width = treeImage.getWidth();
		tree.height = treeImage.getHeight();
		trees.add(tree);
		lastTreeTime = TimeUtils.nanoTime();
	}

	private void spawnHeal() {
		Rectangle heal = new Rectangle();
		heal.x = MathUtils.random(0, Gdx.graphics.getWidth()- healImage.getWidth());
		heal.y = Gdx.graphics.getHeight();
		heal.width = healImage.getWidth();
		heal.height = healImage.getHeight();
		heals.add(heal);
		lastHealTime = TimeUtils.nanoTime();
	}


	@Override
	public void render() { //runs every frame
		//clear screen
		Gdx.gl.glClearColor(.2f, .6f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// process user input
		if(Gdx.input.isTouched()) commandTouched(); //mouse or touch screen
		if(Gdx.input.isKeyPressed(Keys.LEFT)) commandMoveLeft();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) commandMoveRight();
		if(Gdx.input.isKeyPressed(Keys.UP)) commandMoveUp();
		if(Gdx.input.isKeyPressed(Keys.DOWN)) commandMoveDown();
		if(Gdx.input.isKeyPressed(Keys.A)) commandMoveLeftCorner();
		if(Gdx.input.isKeyPressed(Keys.S)) commandMoveRightCorner();
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) commandExitGame();

		// begin a new batch and draw the rocket, astronauts, asteroids
		batch.begin();
		{ //add brackets just for intent
			batch.draw(ambulanceImage, ambulance.x, ambulance.y);
			for (Rectangle tree : trees) {
				batch.draw(treeImage, tree.x, tree.y);

			}
			for (Rectangle syringe : syringes) {
				batch.draw(syringeImage, syringe.x, syringe.y);
			}
			for (Rectangle heal : heals) {
				batch.draw(healImage, heal.x, heal.y);
			}
			font.setColor(Color.WHITE);
			font.draw(batch, "" + vaccineScore, Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 20);
			font.setColor(Color.GREEN);
			font.draw(batch, "" + ambulanceHealth, 20, Gdx.graphics.getHeight() - 20);
		}
		batch.end();

		// check if we need to create a new
		if(TimeUtils.nanoTime() - lastSyringeTime > CREATE_SYRINGE_TIME) spawnSyringe();
		if(TimeUtils.nanoTime() - lastTreeTime > CREATE_TREE_TIME) spawnTree();
		if(TimeUtils.nanoTime() - lastHealTime > CREATE_HEAL_TIME) spawnHeal();

		if (ambulanceHealth > 0) { //is game end?
			// move and remove any that are beneath the bottom edge of
			// the screen or that hit the rocket.
			for (Iterator<Rectangle> iter = trees.iterator(); iter.hasNext(); ) {
				Rectangle tree = iter.next();
				tree.y -= SPEED_TREE * Gdx.graphics.getDeltaTime();
				if (tree.y + treeImage.getHeight() < 0) iter.remove();
				if (tree.overlaps(ambulance)) {
					//astronautSound.play();
					ambulanceHealth--;
				}
			}

			for (Iterator<Rectangle> iter = syringes.iterator(); iter.hasNext(); ) {
				Rectangle syringe = iter.next();
				syringe.y -= SPEED_SYRINGE * Gdx.graphics.getDeltaTime();
				if (syringe.y + syringeImage.getHeight() < 0) iter.remove(); //From screen
				if (syringe.overlaps(ambulance)) {
					//astronautSound.play();
					vaccineScore++;
					if (vaccineScore %10==0){
						SPEED_TREE += 66; //speeds up
						SPEED_SYRINGE += 66;
						SPEED_HEAL += 66;
					}
					iter.remove(); //smart Array enables remove from Array
				}
			}

			for (Iterator<Rectangle> iter = heals.iterator(); iter.hasNext(); ) {
				Rectangle heal = iter.next();
				heal.y -= SPEED_HEAL * Gdx.graphics.getDeltaTime();
				if (heal.y + treeImage.getHeight() < 0) iter.remove();
				if (heal.overlaps(ambulance)) {
					//astronautSound.play();
					ambulanceHealth += 5;
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
		syringeImage.dispose();
		ambulanceImage.dispose();
		astronautSound.dispose();
		batch.dispose();
		font.dispose();
	}
}