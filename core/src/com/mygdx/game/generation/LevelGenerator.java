package com.mygdx.game.generation;

import com.mygdx.game.entities.collisionHandling.Coordinate;
import com.mygdx.game.entities.collisionHandling.EnumDirection;
import com.mygdx.game.entities.collisionHandling.IntCoordinate;
import com.mygdx.game.entities.tiles.FloorTile;
import com.mygdx.game.entities.tiles.NullTile;

public class LevelGenerator {
	private IntCoordinate start = new IntCoordinate(0, 0);
	private Coordinate playerStart;
	private EnumDirection initialDirection = EnumDirection.UP;
	private StochasticGrammar generatorGrammar;
	private final int steps = 30;

	public LevelGenerator() {
		Symbol[] leftHandSides = {
			Symbol.S,
			Symbol.S,
			Symbol.S,
			Symbol.S,
			Symbol.S,
			Symbol.S
		};
		Symbol[][] rightHandSides = {
			{ Symbol.F, Symbol.S },
			{ Symbol.R, Symbol.F, Symbol.F, Symbol.S },
			{ Symbol.L, Symbol.F, Symbol.F, Symbol.S },
			{ Symbol.PUSH, Symbol.S, Symbol.POP, Symbol.L, Symbol.S },
			{ Symbol.PUSH, Symbol.S, Symbol.POP, Symbol.R, Symbol.S },
			{ Symbol.PUSH, Symbol.L, Symbol.S, Symbol.POP, Symbol.PUSH, Symbol.F, Symbol.S, Symbol.POP, Symbol.R, Symbol.S }
		};
		double[] probabilities = {0.5, 0.1, 0.1, 0.1, 0.1, 0.1};
		generatorGrammar = new StochasticGrammar(Symbol.S, leftHandSides, rightHandSides, probabilities);
	}

	public FloorTile[][] generateFloor() {
		LevelDriver driver = new LevelDriver(generatorGrammar);
		driver.injectParameters(start, initialDirection);

		// Generate free tiles of dungeon.
		// TODO: The free tiles should correspond not to floor tiles, but instead to large levels
		Boolean[][] freeTiles = driver.generate(steps);
		FloorTile[][] floor = new FloorTile[freeTiles.length + 2][freeTiles[0].length + 2];

		// Surround the dungeon area with a wall that's at least a FloorTile thick.
		for (int j = 0; j < floor[0].length; j++) {
			floor[0][j] = new FloorTile(0, j);
			floor[floor.length - 1][j] = new FloorTile(floor.length - 1, j);
		}
		for (int i = 0; i < floor.length; i++) {
			floor[i][0] = new FloorTile(i, 0);
			floor[i][floor[0].length - 1] = new FloorTile(i, floor[0].length - 1);
		}

		// Fill in FloorTiles into `floor` according to our `freeTiles`.
		for (int i = 1; i < floor.length - 1; i++) {
			FloorTile[] row = floor[i];
			for (int j = 1; j < row.length - 1; j++) {
				if (freeTiles[i - 1][j - 1] == null || !freeTiles[i - 1][j - 1]) {
					floor[i][j] = new FloorTile(i, j);
				} else {
					floor[i][j] = new NullTile(i, j);
				}
			}
		}

		// free the position the player is standing on.
		playerStart = new Coordinate(start.getX() - driver.getMinX() + 1, start.getY() - driver.getMinY() + 1);
		//floor[start.getX() - driver.getMinX() + 1][start.getY() - driver.getMinY() + 1] = null;
		
		return floor;
	}

	/**
	 * @return the playerStart
	 */
	public Coordinate getPlayerStart() {
		return playerStart;
	}

}
