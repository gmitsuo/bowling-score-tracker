package com.gamingscore.game.bowling.entities.frames;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeLastFrameTest {

	@Test
	void beforeLastFrameShouldAddStrikeBonusAfterLastFrameIsComplete() {

		BeforeLastFrame beforeLastFrame = new BeforeLastFrame();
		beforeLastFrame.addPlay("10");
		beforeLastFrame.calculateScore();

		LastFrame lastFrame = new LastFrame();
		lastFrame.addPlay("9");
		lastFrame.addPlay("1");
		lastFrame.addPlay("10");
		lastFrame.setPrevious(beforeLastFrame);
		lastFrame.calculateScore();

		assertThat(lastFrame.getScore()).isEqualTo(20);
		assertThat(beforeLastFrame.getScore()).isEqualTo(20);
	}
}