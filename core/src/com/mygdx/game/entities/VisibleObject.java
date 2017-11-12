package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.entities.collisionHandling.BoundingBox;
import com.mygdx.game.entities.collisionHandling.BoundingBoxRectangle;
import com.mygdx.game.view.Look;

public abstract class VisibleObject {
	private Look look;
	private BoundingBoxRectangle boundingBox;

	public VisibleObject() {
		loadLook();
	}

	protected abstract void loadLook();

	protected abstract void makeBoundingBox(double x, double y);

	protected void setLook(Look l) {
		this.look = l;
	}

	protected void setBoundingBox(BoundingBoxRectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	public Texture getCurrentLook() {
		return look.getCurrent();
	}


	public double getX() {
		return boundingBox.getX();
	}

	public double getY() {
		return boundingBox.getY();
	}

	public abstract void tick(double timeSinceLastFrame);

	public boolean collidesWith(VisibleObject v) {
		return boundingBox.collidesWith(v.getBoundingBox());
	}

	public void collisionDisplace(VisibleObject v, int deltaX, int deltaY) {
		boundingBox.displaceOther(v.getBoundingBox(), deltaX, deltaY);
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
}
