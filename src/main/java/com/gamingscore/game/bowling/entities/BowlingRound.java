package com.gamingscore.game.bowling.entities;

import java.util.Objects;
import java.util.StringJoiner;

public class BowlingRound {

	private final String player;
	private final BowlingRoundResult roundResult;

	public BowlingRound(String player, BowlingRoundResult roundResult) {
		this.player = player;
		this.roundResult = roundResult;
	}

	public String getPlayer() {
		return this.player;
	}

	public BowlingRoundResult getRoundResult() {
		return this.roundResult;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BowlingRound that = (BowlingRound) o;
		return Objects.equals(player, that.player) &&
				Objects.equals(roundResult, that.roundResult);
	}

	@Override
	public int hashCode() {
		return Objects.hash(player, roundResult);
	}

	@Override
	public String
	toString() {
		return new StringJoiner(", ", BowlingRound.class.getSimpleName() + "[", "]")
				.add("player=" + player)
				.add("roundResult=" + roundResult)
				.toString();
	}
}
