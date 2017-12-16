package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

// Used to cash input state, such as the last mouse position
public class InputContainer {
	public Vector3 mouseCoord;
	private Camera cam;

	public InputContainer(Camera cam) {
		this.cam = cam;
		mouseCoord = new Vector3();
	}

	public void update() {
		mouseCoord = cam.unproject(mouseCoord.set(Gdx.input.getX(), Gdx.input.getY(), 0));
	}

	public float getMouseX() {
		return cam.unproject(mouseCoord.set(Gdx.input.getX(), Gdx.input.getY(), 0)).x;
	}

	public float getMouseY() {
		return cam.unproject(mouseCoord.set(Gdx.input.getX(), Gdx.input.getY(), 0)).y;
	}
}
