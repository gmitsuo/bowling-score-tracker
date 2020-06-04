package com.gamingscore.game.bowling;

import com.gamingscore.game.bowling.entities.frames.Frame;
import com.gamingscore.game.bowling.entities.frames.LastFrame;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreCalculatorTest {

	@Test
	void calculateWithoutSpareAndWithoutStrike() {

		var frame = new Frame(1);
		frame.addPlay("7");
		frame.addPlay("1");

		var next = new Frame(2);
		next.addPlay("1");
		next.addPlay("7");

		var afterNext = new Frame(3);
		afterNext.addPlay("8");
		afterNext.addPlay("1");

		new ScoreCalculator().calculate(List.of(frame, next, afterNext));

		assertThat(frame.getScore()).isEqualTo(8);
		assertThat(next.getScore()).isEqualTo(8);
		assertThat(afterNext.getScore()).isEqualTo(9);
	}

	@Test
	void calculateWithSpare() {

		var frame = new Frame(1);
		frame.addPlay("7");
		frame.addPlay("3");

		var next = new Frame(2);
		next.addPlay("1");
		next.addPlay("7");

		var afterNext = new Frame(3);
		afterNext.addPlay("8");
		afterNext.addPlay("1");

		new ScoreCalculator().calculate(List.of(frame, next, afterNext));

		assertThat(frame.getScore()).isEqualTo(11);
		assertThat(next.getScore()).isEqualTo(8);
		assertThat(afterNext.getScore()).isEqualTo(9);
	}

	@Test
	void calculateWithStrike() {

		var frame = new Frame(1);
		frame.addPlay("10");

		var next = new Frame(2);
		next.addPlay("1");
		next.addPlay("7");

		var afterNext = new Frame(3);
		afterNext.addPlay("8");
		afterNext.addPlay("1");

		new ScoreCalculator().calculate(List.of(frame, next, afterNext));

		assertThat(frame.getScore()).isEqualTo(18);
		assertThat(next.getScore()).isEqualTo(8);
		assertThat(afterNext.getScore()).isEqualTo(9);
	}

	@Test
	void calculateWithStrikeAfterStrike() {

		var frame = new Frame(1);
		frame.addPlay("10");

		var next = new Frame(2);
		next.addPlay("10");

		var afterNext = new Frame(3);
		afterNext.addPlay("8");
		afterNext.addPlay("1");

		new ScoreCalculator().calculate(List.of(frame, next, afterNext));

		assertThat(frame.getScore()).isEqualTo(28);
		assertThat(next.getScore()).isEqualTo(19);
		assertThat(afterNext.getScore()).isEqualTo(9);
	}

	@Test
	void calculateLastFrameWithSpare() {

		var frame = new LastFrame();
		frame.addPlay("8");
		frame.addPlay("2");
		frame.addPlay("10");

		new ScoreCalculator().calculate(Collections.singletonList(frame));
		assertThat(frame.getScore()).isEqualTo(20);
	}

	@Test
	void calculateLastFrameWithStrike() {

		var frame = new LastFrame();
		frame.addPlay("10");
		frame.addPlay("10");
		frame.addPlay("10");

		new ScoreCalculator().calculate(Collections.singletonList(frame));
		assertThat(frame.getScore()).isEqualTo(30);
	}
}