package com.mygdx.game.entities;

import com.mygdx.game.controller.InputContainer;
import com.mygdx.game.model.Level;

public abstract class Entity extends VisibleObject {
	protected InputContainer inputContainer;
	private Level level;

	public Entity(double x, double y) {
		super();
		makeBoundingBox(x, y);
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
}
