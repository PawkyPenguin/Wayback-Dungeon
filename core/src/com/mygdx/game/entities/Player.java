package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.limiters.AngularSpeedLimiter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.mygdx.game.entities.tiles.FloorTile;
import com.mygdx.game.view.LivingLook;
import com.mygdx.game.view.spriteEnums.LookEnum;

import java.util.Arrays;

public class Player extends Entity {
	final static int SPEED = 800;

	public Player(float x, float y) {
		super(x, y);
		width = 32;
		height = 32;
	}

	@Override
	protected void loadLook() {
		setLook(new LivingLook(LookEnum.player));
	}

	@Override
	protected void loadBoundingBox() {
		Vector3 upperLeft = new Vector3(x, y, 0);
		Vector3 lowerRight = new Vector3(x + width, y + height, 0);
		setBoundingBox(new BoundingBox(upperLeft, lowerRight));
	}

	@Override
	public void tick(double timeSinceLastFrame) {
		float oldX = x;
		float oldY = y;
		if (isKeyPressed(Input.Keys.LEFT)) {
			x -= SPEED * timeSinceLastFrame;
		}
		if (isKeyPressed(Input.Keys.UP)) {
			y += SPEED * timeSinceLastFrame;
		}
		if (isKeyPressed(Input.Keys.RIGHT)) {
			x += SPEED * timeSinceLastFrame;
		}
		if (isKeyPressed(Input.Keys.DOWN)) {
			y -= SPEED * timeSinceLastFrame;
		}
		loadBoundingBox();
		for (FloorTile[] row : getLevel().getFloor()) {
			for (FloorTile floorTile : row) {
				if (floorTile != null && floorTile.collidesWith(this)) {
					this.x = oldX;
					this.y = oldY;
					return;
				}
			}
		}
	}

	private boolean isKeyPressed(int keycode) {
		return Gdx.input.isKeyPressed(keycode);
	}
}
