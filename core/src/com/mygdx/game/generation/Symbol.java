package com.mygdx.game.generation;

// Holds non-terminal symbols for grammar.
// Just add another letter when a new one is needed. Enums are nicer than ints because they're more readable.
public enum Symbol {
	S, F, L, R, PUSH, POP;
}
