package com.mygdx.game.entities.collisionHandling;

public abstract class BoundingBox {

	protected final static double EPS = 1E-2;

	public abstract void move(double x, double y);

	public void move(Coordinate c) {
		move(c.x, c.y);
	}

	public abstract boolean collidesWith(BoundingBox b);

	protected abstract boolean collidesVisit(BoundingBoxRectangle b);

	public abstract void displaceOther(BoundingBox b, int deltaX, int deltaY);

	protected abstract void displaceVisit(BoundingBoxRectangle b, int deltaX, int deltaY);

	public abstract double getX();

	public abstract double getY();

	public abstract void setX(double x);

	public abstract void setY(double y);
}
