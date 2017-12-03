package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.controller.InputContainer;
import com.mygdx.game.model.Level;

public abstract class Entity extends VisibleObject implements CameraBearer{
	protected InputContainer inputContainer;
	private Camera cam;
	private Level level;

	public Entity(double x, double y) {
		super();
		makeBoundingBox(x, y);
	}

	public Entity() {
		super();
	}

	public void injectLevel(Level l) {
		this.level = l;
	}

	public void setInputContainer(InputContainer inputContainer) {
		this.inputContainer = inputContainer;
	}

	public Level getLevel() {
		return level;
	}

	@Override
	public void registerCamera(Camera c) {
		cam = c;
	}

	@Override
	public void updateCamera() {
		cam.position.set((float) getX(), (float) getY(), 0);
	}
}
