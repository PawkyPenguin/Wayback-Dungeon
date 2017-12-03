package com.mygdx.game.model;

import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.collisionHandling.Coordinate;

public class Game {
	private Level level;
	private Player player;

	public Game() {
	}

	public void begin() {
		level = new Level();
		level.createFloor();
		Coordinate playerPosition = level.choosePlayerPosition();
		player.injectPosition(playerPosition);
		player.injectLevel(level);
	}

	public void tick(long timeSinceLastFrame) {
		level.tick(timeSinceLastFrame);
	}

	public Level getLevel() {
		return level;
	}

	public void setPlayer(Player p) {
		player = p;
	}
}
