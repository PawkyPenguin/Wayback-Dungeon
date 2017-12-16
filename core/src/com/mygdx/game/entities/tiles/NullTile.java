package com.mygdx.game.entities.tiles;

import com.mygdx.game.WaybackDungeon;
import com.mygdx.game.entities.VisibleObject;

public class NullTile extends FloorTile {

	public NullTile(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean collidesWith(VisibleObject v) { 
		return false;
	}

	@Override
	public void disposeLook() { }

	@Override
	public void requestDraw(WaybackDungeon w) { }

	@Override
	public void visitTick(double timeSinceLastFrame) { }

	@Override
	protected void loadLook() { }

	@Override
	protected void makeBoundingBox(double x, double y) { }
	
}
