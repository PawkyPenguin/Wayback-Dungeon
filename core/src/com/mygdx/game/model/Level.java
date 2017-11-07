package com.mygdx.game.model;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.tiles.FloorTile;

import java.util.ArrayList;

public class Level {
	private Player player;
	private ArrayList<Entity> monsters;
	private FloorTile[][] floor;

	public Level() {
		floor = new FloorTile[20][20];
		monsters = new ArrayList<>();
		for (int i = 0; i < floor.length; i++) {
			FloorTile[] row = floor[i];
			for (int j = 0; j < row.length; j++) {
				if (Math.random() > 0.8) {
					floor[i][j] = new FloorTile(i, j);
				}
			}
		}
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

}
