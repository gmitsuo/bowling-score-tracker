package com.gamingscore.game.bowling;

import com.gamingscore.game.bowling.entities.BowlingPlayerScoring;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

public class BowlingScoreDisplay {

	private static final String CARET_RETURN_LINE_FEED = "\r\n";

	public void display(final Collection<BowlingPlayerScoring> scores) {

		final StringBuilder resultMsg = new StringBuilder("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
		resultMsg.append(CARET_RETURN_LINE_FEED);

		scores.forEach(bowlingPlayerScoring -> {

			resultMsg.append(bowlingPlayerScoring.getPlayerName())
			.append(CARET_RETURN_LINE_FEED);

			final var pinfalls = new StringBuilder("Pinfalls\t");
			final var score = new StringBuilder("Score\t\t");
			final var totalScore = new AtomicInteger(0);

			bowlingPlayerScoring.getFrames().forEach(frame -> {
				pinfalls.append(frame.getPinfallsForScoreDisplay()).append("\t");
				score.append(totalScore.addAndGet(frame.getScore())).append("\t\t");
			});

			resultMsg
			.append(pinfalls).append(CARET_RETURN_LINE_FEED)
			.append(score).append(CARET_RETURN_LINE_FEED);
		});

		out.println(resultMsg.toString());
	}
}
