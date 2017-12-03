package com.mygdx.game.entities.collisionHandling;

// Honestly, this class is just completely stupid, and I would much rather make it generic,
// however, Java doesn't allow operator overloading, which makes it not possible to add two 
// `Double`s or two `Integer`s cleanly in a generic setting (either that or I'm just being dumb)
public class IntCoordinate implements Cloneable {
	protected int x, y;

	public IntCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;

	}
	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public IntCoordinate move(int x, int y){
		this.x += x;
		this.y += y;
		return this;
	}

	public IntCoordinate move(EnumDirection dir, int distance) {
		switch(dir) {
			case UP:
				y += distance;
				break;	
			case LEFT:
				x -= distance;
				break;
			case DOWN:
				y -= distance;
				break;
			case RIGHT:
				x += distance;
				break;
		}
		return this;
	}

	public IntCoordinate clone() {
		return new IntCoordinate(x, y);
	}
}
