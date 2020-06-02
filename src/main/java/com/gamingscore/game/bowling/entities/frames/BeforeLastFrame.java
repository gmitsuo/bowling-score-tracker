package com.gamingscore.game.bowling.entities.frames;

import com.gamingscore.game.bowling.entities.BowlingPlayerScoring;

import static com.gamingscore.game.bowling.entities.BowlingRoundResult.FOUL;
import static java.lang.Integer.parseInt;

public class BeforeLastFrame extends Frame {

	public BeforeLastFrame() {
		super(BowlingPlayerScoring.LAST_FRAME-1);
	}

	@Override
	protected void addSpareBonus(Frame nextFrame) {
		super.addSpareBonus(nextFrame);
		this.addStrikeBonus(nextFrame);
	}

	private void addStrikeBonus(Frame nextFrame) {

		if (!this.isStrike()) {
			return;
		}

		this.score += !FOUL.equals(nextFrame.getFirstPlay()) ? parseInt(nextFrame.getFirstPlay()) : 0;
		this.score += !FOUL.equals(nextFrame.getSecondPlay()) ? parseInt(nextFrame.getSecondPlay()) : 0;
	}
}
