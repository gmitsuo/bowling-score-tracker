package com.gamingscore.game.bowling.entities.frames;

import com.gamingscore.game.bowling.exception.BowlingGameException;

import java.util.Objects;
import java.util.StringJoiner;

import static com.gamingscore.game.bowling.entities.BowlingRoundResult.FOUL;
import static com.gamingscore.game.bowling.entities.BowlingRoundResult.STRIKE;
import static java.lang.Integer.parseInt;

public class Frame {

	protected final Integer id;

	protected Integer score;
	protected String firstPlay;
	protected String secondPlay;
	protected Frame previous;

	public Frame(final Integer id) {
		this.id = id;
		this.score = 0;
	}

	public Integer getScore() {
		return score;
	}

	public void setPrevious(final Frame previous) {
		this.previous = previous;
	}

	public void addPlay(final String pinFall) {

		if (firstPlay == null){
			this.firstPlay = pinFall;
		}
		else if (secondPlay == null) {
			this.secondPlay = pinFall;
			this.checkSecondPlayConstraints();
		}
		else {
			throw new BowlingGameException("Frames up until the last one must have a maximum of 2 plays.");
		}
	}

	private void checkSecondPlayConstraints() {

		if (STRIKE.equals(firstPlay)) {
			throw new BowlingGameException(String.format("Cannot add second play to frame with a strike. Frame id: %s.", this.id));
		}

		if (FOUL.equals(firstPlay) || FOUL.equals(secondPlay)) {
			return;
		}

		if (parseInt(firstPlay) + parseInt(secondPlay) > 10) {
			throw new BowlingGameException(String.format("The sum of plays cannot be higher than 10. Frame id: %s.", this.id));
		}
	}

	public boolean isComplete() {
		return isStrike() || (firstPlay != null && secondPlay != null);
	}

	public Integer getPinfalls() {
		return (FOUL.equals(this.firstPlay) ? 0 : parseInt(this.firstPlay)) +
				(this.secondPlay == null || FOUL.equals(this.secondPlay) ? 0 :parseInt(this.secondPlay));
	}

	public void addScore(final Integer bonusScore) {
		this.score += bonusScore;
	}

	public String getFirstPlay() {
		return firstPlay;
	}

	public String getSecondPlay() {
		return secondPlay;
	}

	public boolean isSpare() {

		if (this.isStrike()) {
			return false;
		}

		if (this.secondPlay == null) {
			return false;
		}

		return !FOUL.equals(this.firstPlay) && !FOUL.equals(this.secondPlay) &&
				((parseInt(this.firstPlay) + parseInt(this.secondPlay)) == 10);
	}

	public boolean isStrike() {
		return STRIKE.equals(firstPlay);
	}

	public String getPinfallsForScoreDisplay() {
		if (this.isStrike()) {
			return "\tX";
		}
		else if (this.isSpare()) {
			return firstPlay + "\t/";
		}
		return firstPlay + "\t" + secondPlay;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Frame frame = (Frame) o;
		return Objects.equals(id, frame.id) &&
				Objects.equals(score, frame.score) &&
				Objects.equals(firstPlay, frame.firstPlay) &&
				Objects.equals(secondPlay, frame.secondPlay) &&
				Objects.equals(previous, frame.previous);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, score, firstPlay, secondPlay, previous);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Frame.class.getSimpleName() + "[", "]")
				.add("id=" + id)
				.add("score=" + score)
				.add("firstPlay='" + firstPlay + "'")
				.add("secondPlay='" + secondPlay + "'")
				.add("previous=" + previous)
				.toString();
	}
}