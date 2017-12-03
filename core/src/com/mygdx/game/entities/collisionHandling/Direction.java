package com.mygdx.game.entities.collisionHandling;

public class Direction {
	private static EnumDirection UP = EnumDirection.UP;
	private static EnumDirection LEFT = EnumDirection.LEFT;
	private static EnumDirection DOWN = EnumDirection.DOWN;
	private static EnumDirection RIGHT = EnumDirection.RIGHT;
	private static EnumDirection NONE = EnumDirection.NONE;
	private static EnumDirection[] horizontalDirection = {LEFT, NONE, RIGHT};
	private static EnumDirection[] verticalDirection = {DOWN, NONE, UP};

	public static EnumDirection getHorizontalFromDelta(int deltaX) {
		return horizontalDirection[deltaX + 1];
	}

	public static EnumDirection getVerticalFromDelta(int deltaY) {
		return verticalDirection[deltaY + 1];
	}

	public static EnumDirection turnRight(EnumDirection dir) {
		switch(dir) {
			case UP:
				return EnumDirection.RIGHT;
			case LEFT:
				return EnumDirection.UP;
			case DOWN:
				return EnumDirection.LEFT;
			case RIGHT:
				return EnumDirection.DOWN;
		}
		return EnumDirection.NONE;
	}

	public static EnumDirection turnLeft(EnumDirection dir) {
		switch(dir) {
			case UP:
				return EnumDirection.LEFT;
			case LEFT:
				return EnumDirection.DOWN;
			case DOWN:
				return EnumDirection.RIGHT;
			case RIGHT:
				return EnumDirection.UP;
		}
		return EnumDirection.NONE;
	}
}
