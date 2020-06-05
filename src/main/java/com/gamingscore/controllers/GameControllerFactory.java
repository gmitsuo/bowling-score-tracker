package com.gamingscore.controllers;

import com.gamingscore.base.GameController;
import com.gamingscore.game.RoundBasedGame;
import com.gamingscore.game.bowling.BowlingInputChecker;
import com.gamingscore.game.bowling.BowlingScoreDisplay;
import com.gamingscore.game.bowling.entities.BowlingGame;

import static com.gamingscore.game.RoundBasedGame.BOWLING;

public class GameControllerFactory {

	public GameController getGameController(final RoundBasedGame game) {

		if (BOWLING.equals(game)) {
			return new BowlingController(new BowlingGame(), new BowlingInputChecker(), new BowlingScoreDisplay());
		}

		throw new IllegalArgumentException(String.format("Game %s does not exist.", game.name()));
	}
}
