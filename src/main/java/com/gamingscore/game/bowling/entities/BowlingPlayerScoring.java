package com.gamingscore.game.bowling.entities;

import com.gamingscore.game.bowling.entities.frames.Frame;
import com.gamingscore.game.bowling.entities.frames.LastFrame;
import com.gamingscore.game.bowling.exception.BowlingGameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class BowlingPlayerScoring {

	public static final Integer LAST_FRAME = 10;

	private final BowlingPlayer player;
	private final List<Frame> frames;

	private Integer currentFrameId;
	private Frame currentFrame;

	public BowlingPlayerScoring(final BowlingPlayer player) {
		this.player = player;
		this.currentFrameId = 1;
		this.frames = new ArrayList<>(10);
		this.currentFrame = new Frame(currentFrameId);
	}

	public String getPlayerName() {
		return this.player.getName();
	}

	public List<Frame> getFrames() {
		return frames;
	}

	public void addPlay(final BowlingRoundResult roundResult) {

		if (currentFrameId > 10) {
			throw new BowlingGameException(String.format("Bowling games must have only 10 frames per player. Player %s has more than 10 frames", this.player.getName()));
		}

		this.currentFrame.addPlay(roundResult.getPinFalls());

		if (currentFrame.isComplete()) {
			this.frames.add(currentFrame);
			this.currentFrame = getNextFrame(++currentFrameId);
		}
	}

	private Frame getNextFrame(final Integer frameId) {

		if (this.currentFrameId > LAST_FRAME) {
			return null;
		}

		Frame nextFrame;

		if (frameId < LAST_FRAME) {
			nextFrame = new Frame(frameId);
		}
		else {
			nextFrame = new LastFrame();
		}

		nextFrame.setPrevious(currentFrame);
		return nextFrame;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BowlingPlayerScoring that = (BowlingPlayerScoring) o;
		return Objects.equals(player, that.player) &&
				Objects.equals(currentFrame, that.currentFrame);
	}

	@Override
	public int hashCode() {
		return Objects.hash(player, currentFrame);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", BowlingPlayerScoring.class.getSimpleName() + "[", "]")
				.add("player=" + player)
				.add("frames=" + currentFrame)
				.toString();
	}
}