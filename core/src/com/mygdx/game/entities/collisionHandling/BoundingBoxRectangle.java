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
		moveX(x);
		moveY(y);
    }

    @Override
	public void moveX(double x) {
    	x1 += x;
    	x2 += x;
	}

	@Override
	public void moveY(double y) {
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

	/** Handle collision between this and `other` by displacing `other` until it doesn't collide anymore.
	 * @param other The bounding box to be displaced
	 * @param direction The direction `other` moved in when the collision happened.
	 */
    public void displaceOther(BoundingBox other, EnumDirection direction) {
    	/* Switch order. We do this for double dispatching capabilities, because it makes our interface much easier to
		 * maintain. Essentially this results in a mini-visitor-pattern.
		 */
		other.displaceThis(this, direction);
	}

	/** Displaces `this` BoundingBoxRectangle in a way such that it doesn't collide with `other` anymore.
	 * @this The collision that is moving in `direction` and collided with `other`.
	 * @param other The collision box of the second (immovable) object.
	 * @param direction The direction `this` was moving when the collision happened.
	 */
	protected void displaceThis(BoundingBoxRectangle other, EnumDirection direction) {
    	switch(direction) {
			case UP:
				setY2(other.y1);
				break;
			case LEFT:
				setX(other.x2);
				break;
			case DOWN:
				setY(other.y2);
				break;
			case RIGHT:
				setX2(other.x1);
				break;
		}
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
