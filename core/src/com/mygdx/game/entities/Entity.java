package com.mygdx.game.entities;

import com.mygdx.game.controller.InputContainer;
import com.mygdx.game.model.Level;

public abstract class Entity extends VisibleObject {
	protected InputContainer inputContainer;
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	private Level level;

	public Entity(float x, float y) {
		super();
		this.y = y;
		this.x = x;
	}

	public void injectLevel(Level l) {
		this.level = l;
	}

	public void setInputContainer(InputContainer inputContainer) {
		this.inputContainer = inputContainer;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Level getLevel() {
		return level;
	}
}
