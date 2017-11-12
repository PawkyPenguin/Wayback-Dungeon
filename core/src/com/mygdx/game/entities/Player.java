package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.entities.collisionHandling.BoundingBoxRectangle;
import com.mygdx.game.entities.tiles.FloorTile;
import com.mygdx.game.view.LivingLook;
import com.mygdx.game.view.spriteEnums.LookEnum;

public class Player extends Entity {
	final static int SPEED = 300;
	final static int width = 32;
	final static int height = 32;

	public Player(double x, double y) {
		super(x, y);
	}

	@Override
	protected void loadLook() {
		setLook(new LivingLook(LookEnum.player));
	}

	@Override
	protected void makeBoundingBox(double x, double y) {
		setBoundingBox(new BoundingBoxRectangle(x, y, x + width, y + height));
	}

	@Override
	public void tick(double timeSinceLastFrame) {
		int deltaX = 0;
		int deltaY = 0;
		if (isKeyPressed(Input.Keys.LEFT)) {
			deltaX--;
		}
		if (isKeyPressed(Input.Keys.UP)) {
			deltaY++;
		}
		if (isKeyPressed(Input.Keys.RIGHT)) {
			deltaX++;
		}
		if (isKeyPressed(Input.Keys.DOWN)) {
			deltaY--;
		}
		getBoundingBox().move(timeSinceLastFrame * deltaX * SPEED, timeSinceLastFrame * deltaY * SPEED);
		outer: for (FloorTile[] row : getLevel().getFloor()) {
			for (FloorTile floorTile : row) {
				if (floorTile != null && floorTile.collidesWith(this)) {
					floorTile.collisionDisplace(this, deltaX, deltaY);
				}
			}
		}
	}

	private boolean isKeyPressed(int keycode) {
		return Gdx.input.isKeyPressed(keycode);
	}
}
