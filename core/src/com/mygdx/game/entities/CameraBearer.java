package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;

interface CameraBearer {

	public void registerCamera(Camera c);

	void updateCamera();
}
