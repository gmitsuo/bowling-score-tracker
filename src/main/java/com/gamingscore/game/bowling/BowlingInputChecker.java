package com.gamingscore.game.bowling;

import com.gamingscore.game.bowling.exception.BowlingGameInputException;

import java.util.regex.Pattern;

import static com.gamingscore.game.bowling.entities.BowlingRoundResult.FOUL;

public class BowlingInputChecker {

	public static final Pattern ROUND_INPUT_MATCHER = Pattern.compile("^(?<playerName>.+)\\t(?<roundScore>[0-9]{1,2}|F)\\s*$");

	public String[] checkInput(int lineIdx, String inputRow) {

		var playerRoundMatcher = ROUND_INPUT_MATCHER.matcher(inputRow);

		if (!playerRoundMatcher.find()) {
			throw new BowlingGameInputException("First value must be the player's name. Second value must be a number between 0 and 10 or an 'F'.", lineIdx);
		}

		var playerName = playerRoundMatcher.group("playerName");
		var result = playerRoundMatcher.group("roundScore");

		if (!FOUL.equals(result) && Integer.parseInt(result) > 10) {
			throw new BowlingGameInputException("The second value must be between 0 and 10.", lineIdx);
		}

		return new String[]{playerName.trim(), result.trim()};
	}
}
