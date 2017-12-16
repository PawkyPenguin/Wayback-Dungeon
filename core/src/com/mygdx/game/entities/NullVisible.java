package com.mygdx.game.entities;

import com.mygdx.game.WaybackDungeon;

// Null object for all `VisibleObject`s
public class NullVisible extends VisibleObject {

	@Override
	public boolean collidesWith(VisibleObject v) { 
		return false;
	}

	@Override
	public void requestDraw(WaybackDungeon w) { }

	@Override
	public void visitTick(double timeSinceLastFrame) { }

	@Override
	protected void loadLook() { }

	@Override
	protected void makeBoundingBox(double x, double y) { }

	@Override
	public void disposeLook() { }

}
