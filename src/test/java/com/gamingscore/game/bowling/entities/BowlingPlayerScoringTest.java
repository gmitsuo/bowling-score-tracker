package com.gamingscore.game.bowling.entities;

import com.gamingscore.game.bowling.exception.BowlingGameException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BowlingPlayerScoringTest {

	@Test
	void moreThanTenFramesShouldThrowException() {

		BowlingPlayerScoring bowlingPlayerScoring = new BowlingPlayerScoring(new BowlingPlayer("Test"));

		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));
		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));
		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));
		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));
		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));
		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));
		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));
		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));
		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));
		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));
		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));
		bowlingPlayerScoring.addPlay(new BowlingRoundResult("10"));

		assertThatExceptionOfType(BowlingGameException.class)
		.isThrownBy(() -> bowlingPlayerScoring.addPlay(new BowlingRoundResult("10")))
		.withMessage("Bowling games must have only 10 frames per player. Player Test has more than 10 frames");
	}

}