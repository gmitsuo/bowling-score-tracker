package com.gamingscore.controllers;

import com.gamingscore.base.GameController;
import com.gamingscore.game.bowling.BowlingInputChecker;
import com.gamingscore.game.bowling.BowlingScoreDisplay;
import com.gamingscore.game.bowling.entities.BowlingGame;
import com.gamingscore.game.bowling.entities.BowlingRound;
import com.gamingscore.game.bowling.entities.BowlingRoundResult;

public final class BowlingController implements GameController<BowlingRound> {

	private final BowlingGame bowlingGame;
	private final BowlingInputChecker bowlingInputChecker;
	private final BowlingScoreDisplay bowlingScoreDisplay;

	BowlingController(
			BowlingGame bowlingGame,
			BowlingInputChecker bowlingInputChecker,
			BowlingScoreDisplay bowlingScoreDisplay) {

		this.bowlingGame = bowlingGame;
		this.bowlingInputChecker = bowlingInputChecker;
		this.bowlingScoreDisplay = bowlingScoreDisplay;
	}

	@Override
	public String[] checkInput(int lineIdx, String inputRow) {
		return bowlingInputChecker.checkInput(lineIdx, inputRow);
	}

	@Override
	public BowlingRound toGameRound(String[] rowInputs) {
		return new BowlingRound(rowInputs[0], new BowlingRoundResult(rowInputs[1]));
	}

	@Override
	public void playRound(BowlingRound round) {
		bowlingGame.addRound(round);
	}

	@Override
	public void disployResults() {
		bowlingScoreDisplay.display(bowlingGame.getScores());
	}
}
