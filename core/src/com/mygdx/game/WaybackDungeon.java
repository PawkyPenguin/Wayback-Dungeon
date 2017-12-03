package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controller.InputContainer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.tiles.FloorTile;
import com.mygdx.game.entities.tiles.Tile;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Level;

import java.util.ArrayList;

public class WaybackDungeon extends ApplicationAdapter {
	private SpriteBatch batch;
	private SpriteBatch backgroundBatch;
	private Texture background;
	private OrthographicCamera camera;
	private Game game;
	private long lastFrame;
	private ArrayList<Entity> entities;
	private InputContainer inputContainer;

	@Override
	public void create () {
		entities = new ArrayList<Entity>();
		initCam();
		inputContainer = new InputContainer(camera);
		batch = new SpriteBatch();
		backgroundBatch = new SpriteBatch();
		background = new Texture("background.png");

		// initialize player
		Player player = new Player();
		player.setInputContainer(inputContainer);
		player.registerCamera(camera);
		entities.add(player);
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
	public void render () {
		inputContainer.tick();
		camera.update();
		draw();
		long thisFrame = System.currentTimeMillis();
		double timeSinceLastFrame = (thisFrame - lastFrame) / 1000.0;
		lastFrame = thisFrame;
		for (Entity e : entities) {
			e.tick(timeSinceLastFrame);
		}
	}

	private void draw() {
		drawBackground();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawGame();
		for (Entity e : entities) {
			drawEntity(e);
		}
		batch.end();
	}


	private void drawBackground() {
		backgroundBatch.begin();
		backgroundBatch.draw(background, 0, 0);
		backgroundBatch.end();
	}

	private void drawEntity(Entity e) {
		batch.draw(e.getCurrentLook(), (int) e.getX(), (int) e.getY());
	}

	private void drawGame() {
		Level level = game.getLevel();
		for (Entity m : level.getMonsters()) {
			drawEntity(m);
		}
		for (FloorTile[] row : level.getFloor()) {
			for (FloorTile tile : row) {
				drawTile(tile);
			}
		}
	}

	public void drawTile(Tile tile) {
		if (tile == null) {
			return;
		}
		batch.draw(tile.getCurrentLook(), (int) tile.getX(), (int) tile.getY());
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		backgroundBatch.dispose();
		background.dispose();
		for (Entity e : entities) {
			e.getCurrentLook().dispose();
		}
	}
}
