package com.gamingscore.game.bowling.entities.frames;

import com.gamingscore.game.bowling.exception.BowlingGameException;
import org.junit.jupiter.api.Test;

import static com.gamingscore.game.bowling.entities.BowlingRoundResult.FOUL;
import static com.gamingscore.game.bowling.entities.BowlingRoundResult.STRIKE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FrameTest {

	@Test
	void addFrameFirstPlay() {
		Frame frame = new Frame(1);
		frame.addPlay("9");

		assertThat(frame.getFirstPlay()).isNotNull();
		assertThat(frame.getSecondPlay()).isNull();
	}

	@Test
	void addFrameSecondPlay() {
		Frame frame = new Frame(1);
		frame.addPlay("9");
		frame.addPlay("1");

		assertThat(frame.getFirstPlay()).isNotNull();
		assertThat(frame.getSecondPlay()).isNotNull();
	}

	@Test
	void addMoreThanTwoPlaysShouldThrowException() {
		Frame frame = new Frame(1);
		frame.addPlay("7");
		frame.addPlay("2");

		assertThatExceptionOfType(BowlingGameException.class)
				.isThrownBy(() -> frame.addPlay(FOUL))
				.withMessage("Frames up until last one must have a maximum of 2 plays.");
	}

	@Test
	void addSecondPlayToStrikeRegularFrameShouldThrowException() {
		Frame frame = new Frame(9);
		frame.addPlay(STRIKE);

		assertThatExceptionOfType(BowlingGameException.class)
				.isThrownBy(() -> frame.addPlay(FOUL))
				.withMessage("Cannot add second play to frame with a strike. Frame id: 9.");
	}

	@Test
	void addPlayWithPinfallSumHigherThanTenShouldThrowException() {
		Frame frame = new Frame(9);
		frame.addPlay("9");

		assertThatExceptionOfType(BowlingGameException.class)
				.isThrownBy(() -> frame.addPlay("2"))
				.withMessage("The sum of plays cannot be higher than 10. Frame id: 9.");
	}

	@Test
	void regularFrameIsNotCompleteWithOnePlayNotStrike() {
		Frame frame = new Frame(9);
		frame.addPlay("9");
		assertThat(frame.isComplete()).isFalse();
	}

	@Test
	void regularFrameIsCompleteWhenFirstIsStrike() {
		Frame frame = new Frame(9);
		frame.addPlay(STRIKE);
		assertThat(frame.isComplete()).isTrue();
	}

	@Test
	void regularFrameIsCompleteWhenHasTwoPlays() {
		Frame frame = new Frame(9);
		frame.addPlay("9");
		frame.addPlay("1");
		assertThat(frame.isComplete()).isTrue();
	}

	@Test
	void firstPlayWith10PinfallsIsStrike() {
		Frame frame = new Frame(1);
		frame.addPlay("10");
		assertThat(frame.isStrike()).isTrue();
	}

	@Test
	void firstPlayPinfallLessThan10IsNotStrike() {
		Frame frame = new Frame(1);
		frame.addPlay("9");
		assertThat(frame.isStrike()).isFalse();
	}

	@Test
	void firstPlayStrikeIsNotSpare() {
		Frame frame = new Frame(1);
		frame.addPlay("10");
		assertThat(frame.isSpare()).isFalse();
	}

	@Test
	void frameWithoutSecondPlayIsNotSpare() {
		Frame frame = new Frame(1);
		frame.addPlay("9");
		assertThat(frame.isSpare()).isFalse();
	}

	@Test
	void frameWithTwoPlaysWithoutTenPinfallsIsNotSpare() {
		Frame frame = new Frame(1);
		frame.addPlay("8");
		frame.addPlay("1");
		assertThat(frame.isSpare()).isFalse();
	}

	@Test
	void frameWithTwoPlaysAndTenPinfallsIsSpare() {
		Frame frame = new Frame(1);
		frame.addPlay("6");
		frame.addPlay("4");
		assertThat(frame.isSpare()).isTrue();
	}

	@Test
	void sumFramePinfalls() {
		Frame frame = new Frame(2);
		frame.addPlay("6");
		frame.addPlay("3");
		frame.sumFramePinfalls();
		assertThat(frame.getScore()).isEqualTo(9);
	}

	@Test
	void calculateScoreShouldAddSpareBonusToPreviousAndStrikeBonusToBeforePrevious() {

		Frame beforePreviousStrike = new Frame(1);
		beforePreviousStrike.addPlay("10");
		beforePreviousStrike.calculateScore();

		Frame previousSpare = new Frame(2);
		previousSpare.addPlay("9");
		previousSpare.addPlay("1");
		previousSpare.setPrevious(beforePreviousStrike);
		previousSpare.calculateScore();

		Frame currentFrame = new Frame(3);
		currentFrame.addPlay("7");
		currentFrame.addPlay("2");
		currentFrame.setPrevious(previousSpare);
		currentFrame.calculateScore();

		assertThat(currentFrame.getScore()).isEqualTo(9);
		assertThat(previousSpare.getScore()).isEqualTo(17);
		assertThat(beforePreviousStrike.getScore()).isEqualTo(20);
	}

	@Test
	void addStrikeBonusForTwoConsecutivesStrikes() {
		Frame beforePreviousStrike = new Frame(1);
		beforePreviousStrike.addPlay("10");
		beforePreviousStrike.calculateScore();

		Frame previousStrike = new Frame(2);
		previousStrike.addPlay("10");
		previousStrike.setPrevious(beforePreviousStrike);
		previousStrike.calculateScore();

		Frame currentFrame = new Frame(3);
		currentFrame.addPlay("7");
		currentFrame.addPlay("2");
		currentFrame.setPrevious(previousStrike);
		currentFrame.calculateScore();

		Frame nextFrame = new Frame(4);
		nextFrame.addPlay("7");
		nextFrame.addPlay("1");
		nextFrame.setPrevious(currentFrame);
		nextFrame.calculateScore();

		assertThat(nextFrame.getScore()).isEqualTo(8);
		assertThat(currentFrame.getScore()).isEqualTo(9);
		assertThat(previousStrike.getScore()).isEqualTo(19);
		assertThat(beforePreviousStrike.getScore()).isEqualTo(27);
	}
}