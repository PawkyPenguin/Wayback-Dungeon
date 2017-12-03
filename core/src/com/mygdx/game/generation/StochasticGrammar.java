package com.mygdx.game.generation;

import java.util.ArrayList;
import java.util.Arrays;

// TODO: This class certainly should be unit tested.
public class StochasticGrammar {
	/* Grammar driver that is attached to the grammar. From a theoretical point of view, the driver is a turing machine that's attached to the grammar,
	 * which makes it possible to design intricate algorithms more easily. 
	 * Also, the grammar without a driver is just context-free, so we gain power by adding the driver.
	 */
	private GrammarDriver driver;
	private GrammarRule[] rules;
	private ArrayList<Symbol> nonterminals = new ArrayList<Symbol>();
	private double[] probabilities;
	private ArrayList<Symbol> currentWord = new ArrayList<Symbol>();
	private Symbol axiom;

	public StochasticGrammar(Symbol axiom, GrammarRule[] rules, double[] probabilities) {
		if (rules.length != probabilities.length) {
			throw new IllegalArgumentException("Must have as many grammar rules as probability specifications");
		}
		this.rules = rules;
		this.probabilities = probabilities;
		this.axiom = axiom;
		for (GrammarRule rule : rules) {
			if (!rule.isTerminal()) {
				nonterminals.add(rule.getLhs());
			}
		}
	}

	// Constructor for more convenient rule input
	public StochasticGrammar(Symbol axiom, Symbol[] lhss, Symbol[][] rhss, double[] probabilities) {
		if (rhss.length != lhss.length || lhss.length != probabilities.length) {
			throw new IllegalArgumentException("Input arguments have differing length");
		}
		rules = new GrammarRule[lhss.length];
		for (int i = 0; i < lhss.length; i++) {
			rules[i] = new GrammarRule(lhss[i], rhss[i]);
		}
		this.probabilities = probabilities;
		this.currentWord.add(axiom);
	}

	protected boolean isTerminal(Symbol symbol) {
		for (Symbol s : nonterminals) {
			if (s == symbol) {
				return false;
			}
		}
		return true;
	}

	public void registerGrammarDriver(GrammarDriver driver) {
		this.driver = driver;
	}

	private int chooseRandomRule(ArrayList<Integer> matchingRules) {
		double summedProbability = 0;
		for (Integer ruleId : matchingRules) {
			summedProbability += probabilities[ruleId];
		}
		double rng = Math.random() * summedProbability;
		// `rng` is >0 and <summedProbability, so the size of `rng` maps to a particular rule.
		int choice = 0;
		for (Integer ruleId : matchingRules) {
			if (rng < probabilities[ruleId]) {
				choice = ruleId;
				break;
			} else {
				rng -= probabilities[ruleId];
			}
		}
		return choice;
	}

	// Iterate over the whole current word and apply a rule whenever possible.
	public boolean step() {
		// hold all rules and symbols these rules apply to, that are chosen during the step.
		ArrayList<Integer> chosenSymbols = new ArrayList<>();
		ArrayList<Integer> chosenRules = new ArrayList<>();

		// Iterate over whole word and choose fitting rules stochastically for all symbols
		// TODO: This is currently O(n * m). O(n*log(m)) is possible if we sort the rules by lhs.
		for (int i = 0; i < currentWord.size(); i++) {
			Symbol symbol = currentWord.get(i);
			ArrayList<Integer> matchingRules = new ArrayList<Integer>();
			ArrayList<Integer> matchingSymbols = new ArrayList<Integer>();
			// Get matching rules for current nonterminal
			for (int j = 0; j < rules.length; j++) {
				GrammarRule rule = rules[j];
				if (rule.getLhs() == symbol) {
					matchingRules.add(j);
					matchingSymbols.add(i);
				}
			}
			if (matchingRules.isEmpty()) {
				continue;
			}

			int choice = chooseRandomRule(matchingRules);
			chosenRules.add(choice);
			chosenSymbols.add(i);
		}

		boolean hasStepped = false;
		int lastIndex = 0;
		ArrayList<Symbol> newWord = new ArrayList<>(currentWord);

		// Try to apply all chosen rules
		for (int i = 0; i < chosenRules.size(); i++) {
			// rule that we try to apply this iteration
			GrammarRule chosenRule = rules[chosenRules.get(i)];
			// nonterminal that is being replaced by RHS
			Integer chosenSymbolId = chosenSymbols.get(i);
			if (driver.isStepValid(newWord, chosenSymbols.get(i) + lastIndex, chosenRule)) {
				// the rule is valid, so apply it to newWord. We adjust the index, because the word has grown (or shrunk).
				newWord.addAll(chosenSymbolId + lastIndex, Arrays.asList(chosenRule.getRhs()));
				newWord.remove(chosenSymbolId + lastIndex + chosenRule.getRhs().length);
				hasStepped = true;
				lastIndex += chosenRule.getRhs().length - 1;
			}
		}
		currentWord = newWord;
		for (Symbol s : currentWord) {
			System.out.println("Symbol " + s);
		}
		return hasStepped;
	}

	/**
	 * @return the nonterminals
	 */
	public ArrayList<Symbol> getNonterminals() {
		return nonterminals;
	}

	/**
	 * @return the currentWord
	 */
	public ArrayList<Symbol> getCurrentWord() {
		return currentWord;
	}

	/**
	 * @return the axiom
	 */
	public Symbol getAxiom() {
		return axiom;
	}

}
