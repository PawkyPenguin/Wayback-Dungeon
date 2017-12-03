package com.mygdx.game.generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import com.mygdx.game.entities.collisionHandling.Direction;
import com.mygdx.game.entities.collisionHandling.EnumDirection;
import com.mygdx.game.entities.collisionHandling.IntCoordinate;
import com.mygdx.game.generation.LevelDriver.ParserState;
import com.mygdx.game.utility.Plane;

public class LevelDriver extends StatefulDriver<Boolean[][], ParserState> {
	/* Conceptually, we want to be able to generate dungeons randomly, and dungeons may grow in all four
	 * directions, infinitely large. The problem arises that we can't simply take a two-dimensional ArrayList, because that only
	 * allows us to grow into two directions. So conceptually, we take our map and fold it over itself twice. Our "folded" level
	 * therefore only grows in two directions. Of course this means that one element of our 2D-ArrayList maps to up to three coordinates,
	 * so we need to keep some more state - which is kept in the LevelContent class.
	 */
	private Plane<Boolean> generatedLevel = new Plane<Boolean>();
	// ArrayList that contains all generated coordinates within `generatedLevel`. This is redundant, but having O(generated coords) access
	// instead of O(width * length) gives us better runtime when returning all the generated coordinates.
	private ArrayList<IntCoordinate> generatedCoords = new ArrayList<IntCoordinate>();

	public LevelDriver(StochasticGrammar g) {
		super(g);
	}

	@Override
	public void initialize(Symbol axiom) {
		currentWordInfo = new HashMap<Integer, ParserState>();
	}

	public void injectParameters(IntCoordinate initialPosition, EnumDirection initialDirection) {
		currentWordInfo.put(0, new ParserState(initialPosition.clone(), initialDirection));
		addToLevel(initialPosition.clone());
	}

	private void addToLevel(IntCoordinate c) {
		generatedCoords.add(c);
		generatedLevel.addContent(c, true);
	}

	@Override
	public boolean isStepValid(ArrayList<Symbol> currentWord, Integer changedSymbolIndex, GrammarRule appliedRule) {
		// Adds the new symbols into the coordinate map and into the HashMap, and then removes the replaced symbol from the HashMap.
		// The coordinates that have to be added can be gotten from the currentWordInfo, because `changedSymbolIndex` is stored in there.
		ParserState currentState = currentWordInfo.get(changedSymbolIndex).clone();
		System.out.println("Validity check");

		Stack<ParserState> stack = new Stack<>();
		ArrayList<Integer> newNonTerminalIds = new ArrayList<Integer>();
		ArrayList<ParserState> newNonTerminalStates = new ArrayList<ParserState>();
		ArrayList<IntCoordinate> newFreeTiles = new ArrayList<IntCoordinate>();
		int i = 0;
		for (Symbol addedSymbol : appliedRule.getRhs()) {
			switch(addedSymbol) {
				case S:
					newNonTerminalIds.add(changedSymbolIndex + i);
					newNonTerminalStates.add(currentState.clone());
					break;
				case F:
					IntCoordinate oldCoord = currentState.coord.clone();
					currentState.coord.move(currentState.direction, 1);
					if (doesIntersectDungeonArm(currentState.coord, oldCoord)) {
						System.out.println("INVALID");
						return false;
					}
					newFreeTiles.add(currentState.coord.clone());
					break;
				case L:
					currentState.direction = Direction.turnLeft(currentState.direction);
					break;
				case R:
					currentState.direction = Direction.turnRight(currentState.direction);
					break;
				case PUSH:
					stack.push(currentState.clone());
					break;
				case POP:
					currentState = stack.pop();
					break;
			}
			i++;
		}

		// The rule application has succeeded. 
		// Add all new content for the level and the current word to state variables to make it permanent.
		currentWordInfo.remove(changedSymbolIndex);
		ArrayList<ParserState> states = new ArrayList<>();
		ArrayList<Integer> refreshedIds = new ArrayList<>();
		ArrayList<Integer> stateIds = new ArrayList<>(currentWordInfo.keySet());
		// Find out all indices of nonterminals that have to be adjusted, because we changed a Symbol.
		for (Integer content : stateIds) {
			if (content > changedSymbolIndex) {
				states.add(currentWordInfo.remove(content));
				refreshedIds.add(content);
			}
		}
		for (int k = 0; k < refreshedIds.size(); k++) {
			currentWordInfo.put(refreshedIds.get(k) + appliedRule.getRhs().length - 1, states.get(k));
		}
		// Add the nonterminals that were newly inserted into the current word into `currentWordInfo`.
		for (int k = 0; k < newNonTerminalIds.size(); k++) {
			currentWordInfo.put(newNonTerminalIds.get(k), newNonTerminalStates.get(k));
		}
		for (IntCoordinate freeTile : newFreeTiles) {
			addToLevel(freeTile);
		}
		return true;
	}


	// Check the `newCoord` added to the level bumps into any tiles that are already carved out
	private boolean doesIntersectDungeonArm(IntCoordinate newCoord, IntCoordinate oldCoord) {
		int deltaX = newCoord.getX() - oldCoord.getX();
		int deltaY = newCoord.getY() - oldCoord.getY();
		int[] iter = {-1, 0, 1};
		int[] iter2 = {0, 1};

		// The new tile added was carved out vertically
		for (int i : iter) {
			for (int j : iter2) {
				if (i == 0 && j == 0) {
					continue;
				}
				IntCoordinate offsetCoord = new IntCoordinate(newCoord.getX() + i * deltaY + j * deltaX, newCoord.getY() + i * deltaX + j * deltaY); 
				Boolean tileExists = generatedLevel.getContent(offsetCoord);
				// TODO might be mistaken
				if (tileExists != null && tileExists) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Boolean[][] generate(int steps) {
		// TODO: Probably change this return type to a 2D array. Pretty sure we want to get a usable data structure when using this class...
		// Also, to do that, we can just create an ArrayList<IntCoordinate> to keep track of ALL generated IntCoordinate within the dungeon. The 2D field is only
		// needed to check surroundings, but we *really* shouldn't have to parse through the whole thing, because it can easily grow to 100k entries.
		getGrammar().registerGrammarDriver(this);
		for (int i = 0; i < steps; i++) {
			System.out.println("Step " + i);
			getGrammar().step();
		}
		Boolean[][] level = new Boolean[(getMaxX() - getMinX() + 1)][(getMaxY() - getMinY() + 1)];
		for (IntCoordinate generatedCoord : generatedCoords) {
			System.out.println("generatedCoord: " + generatedCoord.getX() + "/" + generatedCoord.getY());
			level[(generatedCoord.getX() - getMinX())][(generatedCoord.getY() - getMinY())] = true;
		}
		return level;
	}

	/**
	 * @return the minX
	 */
	public int getMinX() {
		return generatedLevel.getMinX();
	}

	/**
	 * @return the minY
	 */
	public int getMinY() {
		return generatedLevel.getMinY();
	}

	/**
	 * @return the maxX
	 */
	public int getMaxX() {
		return generatedLevel.getMaxX();
	}

	/**
	 * @return the maxY
	 */
	public int getMaxY() {
		return generatedLevel.getMaxY();
	}

	class ParserState {
		IntCoordinate coord;
		EnumDirection direction;

		public ParserState(IntCoordinate intialPosition, EnumDirection initialDirection) { 
			direction = initialDirection;
			coord = intialPosition;
		}

		public ParserState clone() {
			return new ParserState(coord.clone(), direction);
		}
	}

	// Holds information whether a coordinate is in use. The booleans signify
	// whether the procedurally generated tile in the respective quadrant exists..
	class LevelContent {
		boolean hasLeft, hasBot, hasMain, hasBotLeft = false;

		public LevelContent hasLeft(boolean hasLeft) {
			this.hasLeft = hasLeft;
			return this;
		}

		public LevelContent hasBot(boolean hasBot) {
			this.hasBot = hasBot;
			return this;
		}

		public LevelContent hasMain(boolean hasMain) {
			this.hasMain = hasMain;
			return this;
		}

		public LevelContent hasBotLeft(boolean hasBotLeft) {
			this.hasBotLeft = hasBotLeft;
			return this;
		}

		// Used when a new LevelContent is added to the list to merge it with the already existing content.
		public LevelContent merge(LevelContent other) {
			hasLeft |= other.hasLeft;
			hasBot |= other.hasBot;
			hasMain |= other.hasMain;
			hasBotLeft |= other.hasBotLeft;
			return this;
		}

		public boolean collides(LevelContent other) {
			return hasLeft && other.hasLeft || hasBot && other.hasBot || 
				hasMain && other.hasMain || hasBotLeft && other.hasBotLeft;
		}
	}

}
