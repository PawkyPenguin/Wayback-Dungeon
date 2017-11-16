package com.mygdx.game.generation;

// Context free grammar rule
public class GrammarRule {
	private Symbol lhs;
	private Symbol[] rhs;

	public GrammarRule(Symbol lhs, Symbol[] rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	// We don't really have any terminal symbols, so instead we just say that rules with null rhs are terminal.
	public boolean isTerminal() {
		return rhs == null;
	}

	public Symbol[] getRhs() {
		return rhs;
	}

	public Symbol getLhs() {
		return lhs;
	}
}
