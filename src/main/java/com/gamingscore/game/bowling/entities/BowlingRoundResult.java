package com.gamingscore.game.bowling.entities;

import java.util.Objects;
import java.util.StringJoiner;

public class BowlingRoundResult {

	public static final String FOUL = "F";
	public static final String STRIKE = "10";

	private final String pinFalls;

	public BowlingRoundResult(String pinFalls) {
		this.pinFalls = pinFalls;
	}

	public String getPinFalls() {
		return pinFalls;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BowlingRoundResult that = (BowlingRoundResult) o;
		return Objects.equals(pinFalls, that.pinFalls);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pinFalls);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", BowlingRoundResult.class.getSimpleName() + "[", "]")
				.add("score='" + pinFalls + "'")
				.toString();
	}
}
