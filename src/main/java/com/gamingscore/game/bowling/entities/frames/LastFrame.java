package com.gamingscore.game.bowling.entities.frames;

import com.gamingscore.game.bowling.exception.BowlingGameException;

import java.util.Objects;
import java.util.StringJoiner;

import static com.gamingscore.game.bowling.entities.BowlingPlayerScoring.LAST_FRAME;
import static com.gamingscore.game.bowling.entities.BowlingRoundResult.FOUL;
import static com.gamingscore.game.bowling.entities.BowlingRoundResult.STRIKE;
import static java.lang.Integer.parseInt;

public class LastFrame extends Frame {

	private String thirdPlay;

	public LastFrame() {
		super(LAST_FRAME);
	}

	public String getThirdPlay() {
		return thirdPlay;
	}

	@Override
	public void addPlay(final String pinFall) {

		if (firstPlay == null) {
			this.firstPlay = pinFall;
		}
		else if (secondPlay == null) {
			this.secondPlay = pinFall;
			this.checkSecondPlayConstraints();
		}
		else if (thirdPlay == null) {
			this.thirdPlay = pinFall;
			this.checkThirdPlayConstraints();
		}
		else {
			throw new BowlingGameException("Last frame must have a maximum of 3 plays.");
		}
	}

	private void checkSecondPlayConstraints() {

		if (STRIKE.equals(firstPlay) || FOUL.equals(firstPlay) || FOUL.equals(secondPlay)) {
			return;
		}

		if (parseInt(firstPlay) + parseInt(secondPlay) > 10) {
			throw new BowlingGameException(String.format("The sum of plays cannot be higher than 10. Frame id: %s.", this.id));
		}
	}

	private void checkThirdPlayConstraints() {

		if (this.isStrike() || this.isSpare()) {
			return;
		}

		throw new BowlingGameException(String.format("Cannot add bonus play to last frame without a strike or spare. Frame id: %s.", this.id));
	}

	@Override
	public Integer getPinfalls() {
		return super.getPinfalls() + (this.thirdPlay != null && !FOUL.equals(this.thirdPlay) ? parseInt(this.thirdPlay) : 0);
	}

	@Override
	public boolean isComplete() {
		return firstPlay != null && secondPlay != null && thirdPlay != null;
	}

	@Override
	public String getPinfallsForScoreDisplay() {

		return ""
		.concat(STRIKE.equals(firstPlay) ? "X" : firstPlay).concat("\t")
		.concat(STRIKE.equals(secondPlay) ? "X" : secondPlay).concat("\t")
		.concat(STRIKE.equals(thirdPlay) ? "X" : thirdPlay);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		final LastFrame frame = (LastFrame) o;
		return Objects.equals(thirdPlay, frame.thirdPlay);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), thirdPlay);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", LastFrame.class.getSimpleName() + "[", "]")
				.add("thirdPlay='" + thirdPlay + "'")
				.toString();
	}
}
