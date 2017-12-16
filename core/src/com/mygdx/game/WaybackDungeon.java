package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controller.InputContainer;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.VisibleObject;
import com.mygdx.game.model.Game;

public class WaybackDungeon extends ApplicationAdapter {
	private SpriteBatch batch;
	private SpriteBatch backgroundBatch;
	private Texture background;
	private OrthographicCamera camera;
	private Game game;
	private long lastFrame;
	private InputContainer inputContainer;

	@Override
	public void create () {
		initCam();
		inputContainer = new InputContainer(camera);
		batch = new SpriteBatch();
		backgroundBatch = new SpriteBatch();
		background = new Texture("background.png");

		// initialize player
		Player player = new Player();
		player.setInputContainer(inputContainer);
		player.registerCamera(camera);
		lastFrame = System.currentTimeMillis();

		// initialize game
		game = new Game();
		game.setPlayer(player);
		game.begin();
	}

	private void initCam() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
	}

	@Override
	public void render() {
		inputContainer.update();
		camera.update();
		draw();
		long thisFrame = System.currentTimeMillis();
		double timeSinceLastFrame = (thisFrame - lastFrame) / 1000.0;
		lastFrame = thisFrame;
		game.visitTick(timeSinceLastFrame);
	}

	private void draw() {
		drawBackground();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		game.requestDraw(this);
		batch.end();
	}

	private void drawBackground() {
		backgroundBatch.begin();
		backgroundBatch.draw(background, 0, 0);
		backgroundBatch.end();
	}

	public void draw(VisibleObject v) {
		batch.draw(v.getCurrentLook(), (int) v.getX(), (int) v.getY());
	}

	@Override
	public void dispose () {
		batch.dispose();
		backgroundBatch.dispose();
		background.dispose();
		game.dispose();
	}
}
