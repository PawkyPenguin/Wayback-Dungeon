package com.mygdx.game.model;

import java.util.ArrayList;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.collisionHandling.Coordinate;
import com.mygdx.game.entities.collisionHandling.EnumDirection;
import com.mygdx.game.entities.tiles.FloorTile;
import com.mygdx.game.generation.LevelGenerator;

public class Level {
	private ArrayList<Entity> monsters;
	private FloorTile[][] floor;
	private static final int width = 30;
	private static final int height = 30;
	private LevelGenerator generator;
	private Coordinate playerStart;

	public Level() {
		generator = new LevelGenerator();
	}

	public void createFloor() {
		monsters = new ArrayList<Entity>();
		floor = generator.generateFloor();
		playerStart = generator.getPlayerStart();
	}

	public void tick(long timeSinceLastFrame) {
		for (FloorTile[] row : floor) {
			for (FloorTile tile : row) {
				tile.tick(timeSinceLastFrame);
			}
		}
		for (Entity m : monsters) {
			m.tick(timeSinceLastFrame);
		}
	}

	public ArrayList<Entity> getMonsters() {
		return monsters;
	}

	public FloorTile[][] getFloor() {
		return floor;
	}

	public Coordinate choosePlayerPosition() {
		// FIXME: Having a conversion here is just straight up stupid. Probably fix this as soon as my promille count is lower than my amount of awake brain cells.
		return new Coordinate(playerStart.getX() * 64, playerStart.getY() * 64);
	}
}
