package com.gamingscore.game.bowling;

import com.gamingscore.game.bowling.exception.BowlingGameInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BowlingInputCheckerTest {

	private BowlingInputChecker bowlingInputChecker;

	@BeforeEach
	void setup() {
		this.bowlingInputChecker = new BowlingInputChecker();
	}

	@Test
	void invalidValueSeparatorShouldThrowExceptionWithInvalidLineNumber() {

		var lineIdx = new Random().nextInt();
		var input = "John    3";

		assertThatExceptionOfType(BowlingGameInputException.class)
		.isThrownBy(() -> bowlingInputChecker.checkInput(lineIdx, input))
		.withMessage("Invalid game input at line "+ lineIdx + ". Input must contain exactly two values separated by a tab (no leading or trailing spaces). " +
				"First value must be player name. Second value must be a number between 0 and 10 or an 'F'.");
	}

	@Test
	void roundScoreDifferentThanNumberOfLetterFShouldThrowExceptionWithInvalidLineNumber() {

		var lineIdx = new Random().nextInt();
		var input = "John\tA";

		assertThatExceptionOfType(BowlingGameInputException.class)
		.isThrownBy(() -> bowlingInputChecker.checkInput(lineIdx, input))
		.withMessage("Invalid game input at line " + lineIdx + ". Input must contain exactly two values separated by a tab (no leading or trailing spaces). " +
				"First value must be player name. Second value must be a number between 0 and 10 or an 'F'.");
	}

	@Test
	void negativeRoundScoreShouldThrowExceptionWithInvalidLineNumber() {

		var lineIdx = new Random().nextInt();
		var input = "John\t-1";

		assertThatExceptionOfType(BowlingGameInputException.class)
		.isThrownBy(() -> bowlingInputChecker.checkInput(lineIdx, input))
		.withMessage("Invalid game input at line " + lineIdx + ". Input must contain exactly two values separated by a tab (no leading or trailing spaces). " +
				"First value must be player name. Second value must be a number between 0 and 10 or an 'F'.");
	}

	@Test
	void scoreHigherThanTenShouldThrowExceptionWithInvalidLineNumer() {

		var lineIdx = new Random().nextInt();
		var input = "John\t11";

		assertThatExceptionOfType(BowlingGameInputException.class)
		.isThrownBy(() -> bowlingInputChecker.checkInput(lineIdx, input))
		.withMessage("Invalid game input at line " + lineIdx + ". Input must contain exactly two values separated by a tab (no leading or trailing spaces). The second value must be a between 0 and 10.");
	}

	@Test
	void decimalScoreThanTenShouldThrowExceptionWithInvalidLineNumer() {

		var lineIdx = new Random().nextInt();
		var input = "John\t1.0";

		assertThatExceptionOfType(BowlingGameInputException.class)
		.isThrownBy(() -> bowlingInputChecker.checkInput(lineIdx, input))
		.withMessage("Invalid game input at line " + lineIdx + ". Input must contain exactly two values separated by a tab (no leading or trailing spaces). " +
				"First value must be player name. Second value must be a number between 0 and 10 or an 'F'.");
	}

}