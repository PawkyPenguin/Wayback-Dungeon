package com.mygdx.game.entities.tiles;

import java.util.Collection;

import com.mygdx.game.WaybackDungeon;
import com.mygdx.game.entities.Drawable;
import com.mygdx.game.entities.Tickable;
import com.mygdx.game.entities.collisionHandling.BoundingBoxRectangle;
import com.mygdx.game.view.TileLook;
import com.mygdx.game.view.spriteEnums.LookEnum;

public class FloorTile extends Tile{

	public FloorTile(int x, int y) {
		super(x, y);
	}

	@Override
	protected void loadLook() {
		setLook(new TileLook(LookEnum.floor1));
	}

	@Override
	protected void makeBoundingBox(double x, double y) {
		BoundingBoxRectangle boundingBox = new BoundingBoxRectangle( x * 64, y * 64, x * 64 + 64, y * 64 + 64);
		setBoundingBox(boundingBox);
	}

	@Override
	public void visitTick(double timeSinceLastFrame) {
		// TODO
	}

	@Override
	public void requestDraw(WaybackDungeon w) {
		// TODO Auto-generated method stub
		w.draw(this);
	}
}
