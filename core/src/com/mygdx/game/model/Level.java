package com.mygdx.game.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import com.mygdx.game.WaybackDungeon;
import com.mygdx.game.entities.Drawable;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Tickable;
import com.mygdx.game.entities.VisibleObject;
import com.mygdx.game.entities.collisionHandling.Coordinate;
import com.mygdx.game.entities.tiles.Tile;
import com.mygdx.game.generation.LevelGenerator;

public class Level implements Tickable, Drawable {
	private ArrayList<Entity> entities;
	private Tile[][] floor;
	private static final int width = 30;
	private static final int height = 30;
	private LevelGenerator generator;
	private Coordinate playerStart;

	public Level() {
		generator = new LevelGenerator();
		entities = new ArrayList<Entity>();
	}

	public void injectPlayer(Player p) {
		entities.add(p);
	}

	public void createFloor() {
		floor = generator.generateFloor();
		playerStart = generator.getPlayerStart();
	}

	// Sorry not sorry
	private Iterable<? extends VisibleObject> iterateVisibles() {
		return new Iterable<VisibleObject>() {
			public Iterator<VisibleObject> iterator() {
				return Stream.concat(entities.stream(), Stream.of(floor).flatMap((e) -> Stream.of(e))).iterator();
			}
		};
	}

	public void disposeVisibles() {
		for (VisibleObject v : iterateVisibles()) {
			v.disposeLook();
		}
	}
	

	public void visitTick(double timeSinceLastFrame) {
		for (VisibleObject v : iterateVisibles()) {
			v.visitTick(timeSinceLastFrame);
		}
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public Tile[][] getFloor() {
		return floor;
	}

	public Coordinate choosePlayerPosition() {
		// FIXME: Having a conversion here is just straight up stupid. Probably fix this as soon as my promille count is lower than my amount of awake brain cells.
		return new Coordinate(playerStart.getX() * 64, playerStart.getY() * 64);
	}

	@Override
	public void requestDraw(WaybackDungeon w) {
		for (VisibleObject v : iterateVisibles()) {
			v.requestDraw(w);
		}
	}
}
