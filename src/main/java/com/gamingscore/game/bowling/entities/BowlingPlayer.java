package com.gamingscore.game.bowling.entities;

import com.gamingscore.base.Player;

import java.util.Objects;
import java.util.StringJoiner;

public class BowlingPlayer implements Player {

	private final String name;

	public BowlingPlayer(final String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final BowlingPlayer that = (BowlingPlayer) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", BowlingPlayer.class.getSimpleName() + "[", "]")
				.add("name='" + name + "'")
				.toString();
	}
}
