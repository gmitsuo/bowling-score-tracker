package com.gamingscore.game.bowling.exception;

public class BowlingGameInputException extends RuntimeException {

	public BowlingGameInputException(final String message, final int lineIdx) {
		super(String.format("Invalid game input at line %s. Input must contain exactly two values separated by a tab (no leading or trailing spaces). %s", lineIdx, message));
	}
}
