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
}
