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

	public Frame(Integer id) {
		this.id = id;
		this.score = 0;
	}

	public Integer getScore() {
		return score;
	}

	public void setPrevious(Frame previous) {
		this.previous = previous;
	}

	public void addPlay(String pinFall) {

		if (firstPlay == null){
			this.firstPlay = pinFall;
		}
		else if (secondPlay == null) {
			this.secondPlay = pinFall;
			this.checkSecondPlayConstraints();
		}
		else {
			throw new BowlingGameException("Frames up until last one must have a maximum of 2 plays.");
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

	public void calculateScore() {

		sumFramePinfalls();

		if (this.previous != null) {
			this.previous.addSpareBonus(this);
		}
	}

	protected String getFirstPlay() {
		return firstPlay;
	}

	protected String getSecondPlay() {
		return secondPlay;
	}

	protected void sumFramePinfalls() {
		this.score += !FOUL.equals(this.firstPlay) ? parseInt(this.firstPlay) : 0;
		this.score += this.secondPlay != null && !FOUL.equals(this.secondPlay) ? parseInt(this.secondPlay) : 0;
	}

	protected void addSpareBonus(Frame nextFrame) {

		if (this.isSpare() && !FOUL.equals(nextFrame.getFirstPlay())) {
			this.score += parseInt(nextFrame.getFirstPlay());
		}

		if (this.previous != null) {
			this.previous.addStrikeBonus(this, nextFrame);
		}
	}

	protected void addStrikeBonus(Frame firstNext, Frame secondNext) {

		if (!this.isStrike()) {
			return;
		}

		this.score += !FOUL.equals(firstNext.getFirstPlay()) ? parseInt(firstNext.getFirstPlay()) : 0;

		if (!firstNext.isStrike()) {
			this.score += !FOUL.equals(firstNext.getSecondPlay()) ? parseInt(firstNext.getSecondPlay()) : 0;
		}
		else {
			this.score += !FOUL.equals(secondNext.getFirstPlay()) ? parseInt(secondNext.getFirstPlay()) : 0;
		}
	}

	protected boolean isSpare() {

		if (this.isStrike()) {
			return false;
		}

		if (this.secondPlay == null) {
			return false;
		}

		return !FOUL.equals(this.firstPlay) && !FOUL.equals(this.secondPlay) &&
				((parseInt(this.firstPlay) + parseInt(this.secondPlay)) == 10);
	}

	protected boolean isStrike() {
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Frame frame = (Frame) o;
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