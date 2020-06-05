package com.gamingscore.game.bowling;

import com.gamingscore.game.bowling.entities.frames.Frame;

import java.util.List;

import static com.gamingscore.game.bowling.entities.BowlingRoundResult.FOUL;
import static java.lang.Integer.parseInt;

public class ScoreCalculator {

	public void calculate(final List<Frame> frames) {

		for (int i = 0; i < frames.size(); i++) {
			final var firstFrame = frames.get(i);
			final var secondFrame = i + 1 < frames.size() ? frames.get(i + 1) : null;
			final var thirdFrame = i + 2 < frames.size() ? frames.get(i + 2) : null;
			this.calculateFrameScore(firstFrame, secondFrame, thirdFrame);
		}
	}

	public void calculateFrameScore(final Frame frame, final Frame next, final Frame afterNext) {

		final var pinfalls = frame.getPinfalls();
		frame.addScore(pinfalls);

		if (next == null)
			return;

		if (frame.isSpare()) {
			final var spareBonus = FOUL.equals(next.getFirstPlay()) ? 0 : parseInt(next.getFirstPlay());
			frame.addScore(spareBonus);
		}
		else if(frame.isStrike()) {
			var strikeBonus = FOUL.equals(next.getFirstPlay()) ? 0: parseInt(next.getFirstPlay());
			strikeBonus += next.getSecondPlay() == null || FOUL.equals(next.getSecondPlay()) ? 0 : parseInt(next.getSecondPlay());
			strikeBonus += next.getSecondPlay() == null && afterNext != null && !FOUL.equals(afterNext.getFirstPlay()) ? parseInt(afterNext.getFirstPlay()) : 0;
			frame.addScore(strikeBonus);
		}
	}
}
