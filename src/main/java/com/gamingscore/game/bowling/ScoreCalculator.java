package com.gamingscore.game.bowling;

import com.gamingscore.game.bowling.entities.frames.Frame;

import java.util.List;

import static com.gamingscore.game.bowling.entities.BowlingRoundResult.FOUL;
import static java.lang.Integer.parseInt;

public class ScoreCalculator {

	public void calculate(final List<Frame> frames) {

		for (int i = 0; i < frames.size(); i++) {
			var firstFrame = frames.get(i);
			var secondFrame = i + 1 < frames.size() ? frames.get(i + 1) : null;
			var thirdFrame = i + 2 < frames.size() ? frames.get(i + 2) : null;
			this.calculateFrameScore(firstFrame, secondFrame, thirdFrame);
		}
	}

	public void calculateFrameScore(final Frame frame, final Frame next, final Frame afterNext) {

		var pinfalls = frame.getPinfalls();
		frame.addScore(pinfalls);

		if (frame.isSpare() && next != null) {
			var spareBonus = !FOUL.equals(next.getFirstPlay()) ? parseInt(next.getFirstPlay()) : 0;
			frame.addScore(spareBonus);
		}
		else if(frame.isStrike()) {
			var strikeBonus = next != null && !FOUL.equals(next.getFirstPlay()) ? parseInt(next.getFirstPlay()) : 0;
			strikeBonus += next != null && next.getSecondPlay() != null && !FOUL.equals(next.getSecondPlay()) ? parseInt(next.getSecondPlay()) : 0;
			strikeBonus += next != null && next.getSecondPlay() == null && afterNext != null && !FOUL.equals(afterNext.getFirstPlay()) ? parseInt(afterNext.getFirstPlay()) : 0;
			frame.addScore(strikeBonus);
		}
	}
}
