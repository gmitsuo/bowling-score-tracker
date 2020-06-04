package com.gamingscore.game.bowling.entities;

import com.gamingscore.game.bowling.ScoreCalculator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BowlingGame {

	final Map<String, BowlingPlayerScoring> scoring;
	final ScoreCalculator scoreCalculator;

	public BowlingGame() {
		this.scoring = new HashMap<>();
		this.scoreCalculator = new ScoreCalculator();
	}

	public void addRound(BowlingRound round) {

		var playerName = round.getPlayer();
		var roundResult = round.getRoundResult();
		var playerScoring = scoring.get(playerName);

		if (playerScoring == null) {
			playerScoring = new BowlingPlayerScoring(new BowlingPlayer(playerName));
			scoring.put(playerName, playerScoring);
		}

		playerScoring.addPlay(roundResult);
	}

	public void calculateScores() {
		scoring.values().stream()
		.map(BowlingPlayerScoring::getFrames)
		.forEach(scoreCalculator::calculate);
	}

	public Collection<BowlingPlayerScoring> getScores() {
		return scoring.values();
	}
}
