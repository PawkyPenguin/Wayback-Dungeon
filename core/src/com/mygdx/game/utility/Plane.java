package com.mygdx.game.utility;

import java.util.ArrayList;

import com.mygdx.game.entities.collisionHandling.IntCoordinate;

public class Plane<TContent> {
	private ArrayList<ArrayList<Field>> contents = new ArrayList<ArrayList<Field>>();
	private int minX, minY, maxX, maxY = 0;

	public void addContent(IntCoordinate c, TContent content) {
		// Clone coordinate, because we don't want sideeffects.
		c = c.clone();
		if (c.getX() < minX) {
			minX = c.getX();
		} else if (c.getX() > maxX) {
			maxX = c.getX();
		}
		if (c.getY() < minY) {
			minY = c.getY();
		} else if (c.getY() > maxY) {
			maxY = c.getY();
		}

		// Transform `c` into the coordinate space spanned by `contents` by packing it into a Field.
		int quadrant = transformToPlaneSpace(c);
		// If the resulting transformed `c` does not fit into `contents`, just make `contents` bigger.
		if (c.getX() >= contents.size()) {
			for (int i = contents.size(); i < c.getX() + 1; i++) {
				ArrayList<Field> newList = new ArrayList<>();
				fillToSize(c.getX() + 1, newList);
				contents.add(newList);
			}
		}
		// Similarly, if the y-coordinate does not fit, make `contents` larger.
		if (c.getY() >= contents.get(c.getX()).size()) {
			for (ArrayList<Field> col : contents) {
				fillToSize(c.getY() + 1, col);
			}
		}
		// Put the newly generated field into our contents
		contents.get(c.getX()).get(c.getY()).set(content, quadrant);
	}

	// Given an ArrayList, fill it up to a certain size.
	private void fillToSize(int size, ArrayList<Field> column) {
		for (int i = column.size(); i < size; i++) {
			column.add(new Field());
		}
	}


	/** Take IntCoordinate c and transform it into the space of our plane.
	 *  @return the quadrant it belongs to.
	 */
	private int transformToPlaneSpace(IntCoordinate c) {
		if (c.getX() >= 0 && c.getY() >= 0) {
			return 0;
		} else if (c.getX() < 0 && c.getY() >= 0) {
			c.setX(-c.getX() + 1);
			return 1;
		} else if (c.getX() < 0 && c.getY() < 0) {
			c.setX(-c.getX() + 1);
			c.setY(-c.getY() + 1);
			return 2;
		} else {
			c.setY(-c.getY() + 1);
			return 3;
		}
	}

	public TContent getContent(IntCoordinate c) {
		int quadrant = transformToPlaneSpace(c);
		if (contents.size() <= c.getX() || contents.get(c.getX()).size() <= c.getY()) {
			return null;
		}
		return contents.get(c.getX()).get(c.getY()).get(quadrant);
	}

	/**
	 * @return the minX
	 */
	public int getMinX() {
		return minX;
	}

	/**
	 * @return the minY
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * @return the maxX
	 */
	public int getMaxX() {
		return maxX;
	}

	/**
	 * @return the maxY
	 */
	public int getMaxY() {
		return maxY;
	}

	// Holds information whether a coordinate is in use. The booleans signify
	// whether the procedurally generated tile in the respective quadrant exists..
	class Field {
		TContent contentLeft, contentBot, contentMain, contentBotLeft;

		public Field containsLeft(TContent contentLeft) {
			this.contentLeft = contentLeft;
			return this;
		}

		public Field containsBot(TContent contentBot) {
			this.contentBot = contentBot;
			return this;
		}

		public Field containsMain(TContent contentMain) {
			this.contentMain = contentMain;
			return this;
		}

		public Field containsBotLeft(TContent contentBotLeft) {
			this.contentBotLeft = contentBotLeft;
			return this;
		}

		public void set(TContent content, int quadrant) {
			switch(quadrant) {
				case 0:
					contentMain = content;
					break;
				case 1:
					contentLeft = content;
					break;
				case 2:
					contentBotLeft = content;
					break;
				case 3:
					contentBot = content;
					break;
				default:
					throw new IllegalArgumentException("`quadrant` must be between 0 and 3");
			}
		}

		public TContent get(int quadrant) {
			switch(quadrant) {
				case 0:
					return contentMain;
				case 1:
					return contentLeft;
				case 2:
					return contentBotLeft;
				case 3:
					return contentBot;
				default:
					throw new IllegalArgumentException("`quadrant` must be between 0 and 3");
			}
		}

		public boolean collides(Field other) {
			return contentLeft != null && other.contentLeft != null 
				|| contentBot != null && other.contentBot != null
				|| contentMain != null && other.contentMain != null 
				|| contentBotLeft != null && other.contentBotLeft != null;
		}
	}
}
