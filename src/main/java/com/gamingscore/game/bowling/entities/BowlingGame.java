package com.gamingscore.game.bowling.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BowlingGame {

	final Map<String, BowlingPlayerScoring> scoring;

	public BowlingGame() {
		this.scoring = new HashMap<>();
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

	public Collection<BowlingPlayerScoring> getScores() {
		return scoring.values();
	}
}
