package com.mygdx.game.entities;

import java.util.Collection;

public interface Tickable {

	public void visitTick(double timeSinceLastFrame);

}
