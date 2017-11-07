package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.mygdx.game.view.Look;

public abstract class VisibleObject {
	private Look look;
	private BoundingBox boundingBox;

	public VisibleObject() {
		loadLook();
	}

	protected abstract void loadLook();

	protected abstract void loadBoundingBox();

	protected void setLook(Look l) {
		this.look = l;
	}

	protected void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	public Texture getCurrentLook() {
		return look.getCurrent();
	}

	public abstract void tick(double timeSinceLastFrame);

	public boolean collidesWith(VisibleObject v) {
		return boundingBox.intersects(v.getBoundingBox());
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
}
