package com.mygdx.game.entities.tiles;

import com.mygdx.game.entities.VisibleObject;

public abstract class Tile extends VisibleObject {
	private boolean solid;
	private int x;
	private int y;

	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		loadLook();
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
