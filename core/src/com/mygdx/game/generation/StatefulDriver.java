package com.mygdx.game.generation;

import java.util.HashMap;

public abstract class StatefulDriver<T, U> extends GrammarDriver<T> {
	// Holds some state related to the word that is currently being parsed.
	HashMap<Integer, U> currentWordInfo;

	public StatefulDriver(StochasticGrammar g) {
		super(g);
	}



}
