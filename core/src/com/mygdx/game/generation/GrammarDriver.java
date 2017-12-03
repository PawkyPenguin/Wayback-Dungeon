package com.mygdx.game.generation;

import java.util.ArrayList;
import java.util.Stack;

import com.mygdx.game.entities.collisionHandling.Coordinate;
import com.mygdx.game.entities.collisionHandling.Direction;
import com.mygdx.game.entities.collisionHandling.EnumDirection;

public abstract class GrammarDriver<T> {
	private StochasticGrammar grammar;
	private int currentStep;
	/* The translation of the word that is being generated. For instance,
	 * if a GrammarDriver is used to generate a mob of monsters, `T` might be an
	 * ArrayList of monsters.
	 */
	private T translatedWord;

	public GrammarDriver(StochasticGrammar grammar) {
		this.grammar = grammar;
		currentStep = 0;
		this.initialize(grammar.getAxiom());
	}

	// Perform some initialization of data structures that the grammar driver will need
	protected abstract void initialize(Symbol axiom);

	// Check if derivation applied to `currentWord` given by `changedSymbolIndex` and `appliedRule` is valid
	protected abstract boolean isStepValid(ArrayList<Symbol> currentWord, Integer changedSymbolIndex, GrammarRule appliedRule);

	// Runs the attached grammar for `steps` amount of steps.
	public abstract T generate(int steps);

	/**
	 * @return the grammar
	 */
	public StochasticGrammar getGrammar() {
		return grammar;
	}

	/**
	 * @return the currentStep
	 */
	public int getCurrentStep() {
		return currentStep;
	}

	/**
	 * @return the translatedWord
	 */
	public T getTranslatedWord() {
		return translatedWord;
	}
}
