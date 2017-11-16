package com.mygdx.game.generation;

import java.util.ArrayList;
import java.util.Arrays;

// TODO: This class certainly should be unit tested.
public class StochasticGrammar {
	private GrammarRule[] rules;
	private double[] probabilities;
	private ArrayList<Symbol> currentWord = new ArrayList<Symbol>();

	public StochasticGrammar(Symbol axiom, GrammarRule[] rules, double[] probabilities) {
		if (rules.length != probabilities.length) {
			throw new IllegalArgumentException("Must have as many grammar rules as probability specifications");
		}
		this.rules = rules;
		this.probabilities = probabilities;
		currentWord.add(axiom);
	}

	// Iterate over the whole current word and apply a rule whenever possible.
	public boolean step() {
		boolean hasStepped = false;
		// copy current word
		ArrayList<Symbol> newWord = new ArrayList<>();
		for (Symbol s : currentWord) {
			newWord.add(s);
		}

		// Iterate over whole word and apply rules to all fitting nonterminals
		// TODO: This is currently O(n^2). O(n*log(n)) is possible if we need to optimize.
		lhsLoop:
		for (int i = 0; i < currentWord.size(); i++) {
			Symbol symbol = currentWord.get(i);
			ArrayList<Integer> matchingRules = new ArrayList<>();
			// Get matching rules for current nonterminal
			for (int j = 0; j < rules.length; j++) {
				GrammarRule rule = rules[j];
				if (rule.getLhs() == symbol) {
					if (rule.isTerminal()) {
						// if *any* rules say that the symbol is a terminal, go to next symbol
						continue lhsLoop;
					} else {
						matchingRules.add(j);
					}
				}
			}
			if (matchingRules.isEmpty()) {
				continue;
			}

			// For current nonterminal, choose one rule probabilistically
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

			// Apply randomly chosen rule
			newWord.remove(choice);
			newWord.addAll(choice, Arrays.asList(rules[choice].getRhs()));
			hasStepped = true;
		}
		return hasStepped;
	}
}
