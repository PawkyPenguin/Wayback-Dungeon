package com.mygdx.game.entities.tiles;

import com.mygdx.game.entities.VisibleObject;

public abstract class Tile extends VisibleObject {
	private boolean solid;

	public Tile(int x, int y) {
		makeBoundingBox(x, y);
		loadLook();
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
}
