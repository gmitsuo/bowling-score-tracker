package com.gamingscore.game.bowling.entities.frames;

import com.gamingscore.game.bowling.exception.BowlingGameException;
import org.junit.jupiter.api.Test;

import static com.gamingscore.game.bowling.entities.BowlingRoundResult.FOUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class LastFrameTest {

	@Test
	void addFrameThirdPlay() {
		LastFrame frame = new LastFrame();
		frame.addPlay("7");
		frame.addPlay("3");
		frame.addPlay("2");

		assertThat(frame.getFirstPlay()).isNotNull();
		assertThat(frame.getSecondPlay()).isNotNull();
		assertThat(frame.getThirdPlay()).isNotNull();
	}

	@Test
	void addMoreThanThreePlaysToLastFrameShouldThrowException() {
		LastFrame frame = new LastFrame();
		frame.addPlay("7");
		frame.addPlay("3");
		frame.addPlay("1");

		assertThatExceptionOfType(BowlingGameException.class)
		.isThrownBy(() -> frame.addPlay(FOUL))
		.withMessage("Last frame must have a maximum of 3 plays.");
	}

	@Test
	void tenthFrameIsNotCompleteAfterTwoPlays() {
		LastFrame frame = new LastFrame();
		frame.addPlay("9");
		frame.addPlay("1");

		assertThat(frame.isComplete()).isFalse();
	}

	@Test
	void tenthFrameIsCompleteAfterThreePlays() {
		LastFrame frame = new LastFrame();
		frame.addPlay("9");
		frame.addPlay("1");
		frame.addPlay("2");

		assertThat(frame.isComplete()).isTrue();
	}

	@Test
	void sumPinfalls() {
		LastFrame frame = new LastFrame();
		frame.addPlay("10");
		frame.addPlay("9");
		frame.addPlay("8");

		var pinfalls = frame.getPinfalls();
		assertThat(pinfalls).isEqualTo(27);
	}

	@Test
	void twoFirstPlaysFromLastFrameSumMoreThan10WithoutStrikeShouldThrowException() {
		LastFrame frame = new LastFrame();
		frame.addPlay("9");

		assertThatExceptionOfType(BowlingGameException.class)
		.isThrownBy(() -> frame.addPlay("9"))
		.withMessage("The sum of plays cannot be higher than 10. Frame id: 10.");
	}

	@Test
	void addBonusPlayWithoutStrikeOrSpareShouldThrowException() {
		LastFrame frame = new LastFrame();
		frame.addPlay("7");
		frame.addPlay("2");

		assertThatExceptionOfType(BowlingGameException.class)
		.isThrownBy(() -> frame.addPlay("6"))
		.withMessage("Cannot add bonus play to last frame without a strike or spare. Frame id: 10.");
	}
}