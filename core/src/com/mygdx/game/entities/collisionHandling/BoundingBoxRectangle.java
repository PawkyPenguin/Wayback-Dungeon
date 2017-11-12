package com.mygdx.game.entities.collisionHandling;

public class BoundingBoxRectangle extends BoundingBox{
	protected double x1, x2, y1, y2;
	private double width, height;

    public BoundingBoxRectangle(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        width = x2 - x1;
        height = y2 - y1;
    }

    @Override
    public void move(double x, double y) {
    	x1 += x;
    	x2 += x;
    	y1 += y;
    	y2 += y;
    }

    @Override
	public double getX() {
    	return x1;
	}

	@Override
	public double getY() {
		return y1;
	}

	@Override
	public void setX(double x) {
    	x1 = x;
    	x2 = x + width;
	}

	@Override
	public void setY(double y) {
    	y1 = y;
    	y2 = y + height;
	}

	private void setX2(double x) {
    	x2 = x;
    	x1 = x - width;
	}

	private void setY2(double y) {
    	y2 = y;
    	y1 = y - height;
	}

	@Override
	public boolean collidesWith(BoundingBox b) {
    	return b.collidesVisit(this);
	}

	@Override
    protected boolean collidesVisit(BoundingBoxRectangle b) {
		if ((x1 - b.x2 < 0) && (b.x1 - x2 < 0) && (y2 - b.y1 > 0) && (b.y2 - y1 > 0)) {
			return true;
		}
		return false;
    }

	/** Takes two bounding boxes that collide and the direction vector (deltaX, deltaY) that box 1 was moving in when
	 * the collision happened.
	 * The returned vector offsets box1 such that it no longer collides with box2, Mario N64 style.
	 * @this The collision box of the first object.
	 * @param other The collision box of the second object.
	 * @return The vector that offsets box1 such that it no longer collides with box2.
	 */
	@Override
    public void displaceOther(BoundingBox other, int deltaX, int deltaY) {
    	other.displaceVisit(this, deltaX, deltaY);
	}

	/* TODO: This method only works if the player moves into x and y at the exactly same speed.
	 * "Throw" player out of wall. If the player has moved diagonally into the wall, we compare how far he has moved
	 * into the wall to determine which edge of `other` he collided with first and adjust throwback accordingly.
	 */
	@Override
	protected void displaceVisit(BoundingBoxRectangle other, int deltaX, int deltaY) {
		switch (deltaX) {
			case 0:
				switch (deltaY) {
					case 0:
						return;
					case 1:
						setY2(other.y1 - EPS);
						return;
					case -1:
						setY(other.y2 + EPS);
						return;
				}
				break;
			case 1:
				switch (deltaY) {
					case 0:
						setX2(other.x1 - EPS);
						return;
					case -1:
						if (x2 - other.x1 < other.y2 - y1) {
							setX2(other.x1 - EPS);
						} else {
							setY(other.y2 + EPS);
						}
						return;
					case 1:
						if (x2 - other.x1 < y2 - other.y1) {
							setX2(other.x1 - EPS);
						} else {
							setY2(other.y1 - EPS);
						}
						return;
				}
				break;
			case -1:
				switch (deltaY) {
					case 0:
						setX(other.x2 + EPS);
						return;
					case -1:
						if (other.x2 - x1 < other.y2 - y1) {
							setX(other.x2 + EPS);
						} else {
							setY(other.y2 + EPS);
						}
						return;
					case 1:
						if (other.x2 - x1 < y2 - other.y1) {
							setX(other.x2 + EPS);
						} else {
							setY2(other.y1 - EPS);
						}
						return;
				}
				break;
		}
		throw new IllegalArgumentException("Missing case or missing return/break.");
	}

    public boolean isPositionInBoundingBox(double x, double y) {
    	if((x > x1) && (x < x2) && (y > y1) && (y < y2)){
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}
