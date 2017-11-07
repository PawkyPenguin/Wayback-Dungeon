package com.mygdx.game.model;

public class Game {
	private Level level;

	public Game() {
		level = new Level();
	}

	public void tick(long timeSinceLastFrame) {
		level.tick(timeSinceLastFrame);
	}

	public Level getLevel() {
		return level;
	}
}
