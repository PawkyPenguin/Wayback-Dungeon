package com.mygdx.game.entities.tiles;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.mygdx.game.view.TileLook;
import com.mygdx.game.view.spriteEnums.LookEnum;

public class FloorTile extends Tile{

	public FloorTile(int x, int y) {
		super(x, y);
		loadBoundingBox();
	}

	@Override
	protected void loadLook() {
		setLook(new TileLook(LookEnum.floor1));

	}

	@Override
	protected void loadBoundingBox() {
		BoundingBox boundingBox = new BoundingBox(new Vector3(getX() * 64, getY() * 64, 0), new Vector3(getX() * 64 + 64, getY() * 64 + 64, 0));
		setBoundingBox(boundingBox);
	}

	@Override
	public void tick(double timeSinceLastFrame) {

	}
}
