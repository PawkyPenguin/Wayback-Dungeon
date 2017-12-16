package com.mygdx.game.model;

import com.mygdx.game.WaybackDungeon;
import com.mygdx.game.entities.Drawable;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Tickable;
import com.mygdx.game.entities.collisionHandling.Coordinate;

public class Game implements Tickable, Drawable {
	private Level level;
	private Player player;

	public Game() {
	}

	public void begin() {
		level = new Level();
		level.injectPlayer(player);
		level.createFloor();
		Coordinate playerPosition = level.choosePlayerPosition();
		player.injectPosition(playerPosition);
		player.injectLevel(level);
	}

	public void dispose() {
		level.disposeVisibles();
	}

	public Level getLevel() {
		return level;
	}

	public void setPlayer(Player p) {
		player = p;
	}

	@Override
	public void visitTick(double timeSinceLastFrame) {
		level.visitTick(timeSinceLastFrame);
	}

	@Override
	public void requestDraw(WaybackDungeon w) {
		level.requestDraw(w);
	}
}
