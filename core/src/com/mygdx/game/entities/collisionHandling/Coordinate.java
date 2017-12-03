package com.mygdx.game.entities.collisionHandling;

public class Coordinate implements Cloneable {
	protected double x, y;

	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;

	}
	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Coordinate move(double x, double y){
		this.x += x;
		this.y += y;
		return this;
	}

	public Coordinate move(EnumDirection dir, double distance) {
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

	public Coordinate clone() {
		return new Coordinate(x, y);
	}
}
