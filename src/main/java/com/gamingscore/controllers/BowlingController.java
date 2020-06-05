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
			final BowlingGame bowlingGame,
			final BowlingInputChecker bowlingInputChecker,
			final BowlingScoreDisplay bowlingScoreDisplay) {

		this.bowlingGame = bowlingGame;
		this.bowlingInputChecker = bowlingInputChecker;
		this.bowlingScoreDisplay = bowlingScoreDisplay;
	}

	@Override
	public String[] checkInput(final int lineIdx, final String inputRow) {
		return bowlingInputChecker.checkInput(lineIdx, inputRow);
	}

	@Override
	public BowlingRound toGameRound(final String... rowInputs) {
		return new BowlingRound(rowInputs[0], new BowlingRoundResult(rowInputs[1]));
	}

	@Override
	public void playRound(final BowlingRound round) {
		bowlingGame.addRound(round);
	}

	@Override
	public void finish() {
		bowlingGame.calculateScores();
	}

	@Override
	public void disployResults() {
		bowlingScoreDisplay.display(bowlingGame.getScores());
	}
}
