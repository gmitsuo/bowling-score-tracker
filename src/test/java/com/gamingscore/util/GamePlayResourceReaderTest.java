package com.gamingscore.util;

import com.gamingscore.base.GameController;
import com.gamingscore.controllers.GameControllerFactory;
import org.junit.jupiter.api.Test;

import static com.gamingscore.game.RoundBasedGame.BOWLING;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

class GamePlayResourceReaderTest {

	@Test
	public void play() {

		var resource = this.getClass().getClassLoader().getResourceAsStream("gameinputs/five-entries.txt");
		assert resource != null;

		GameController<?> gameController = mock(GameController.class);
		var gameControllerFactory = mock(GameControllerFactory.class);

		when(gameControllerFactory.getGameController(BOWLING))
		.thenReturn(gameController);

		when(gameController.checkInput(anyInt(), anyString()))
		.thenReturn(new String[]{"JohnDoe", "F"});

		when(gameController.toGameRound(any(String[].class)))
		.thenReturn(any());

		new GamePlayResourceReader(gameControllerFactory).play(resource, BOWLING);

		verify(gameController, times(5)).checkInput(anyInt(), anyString());
		verify(gameController, times(5)).toGameRound(any(String[].class));
		verify(gameController, times(5)).playRound(any());
		verify(gameController, times(1)).disployResults();
	}

	@Test
	void twoPlayers() {

		var resource = this.getClass().getClassLoader().getResourceAsStream("gameinputs/two-players.txt");
		assert resource != null;

		new GamePlayResourceReader(new GameControllerFactory()).play(resource, BOWLING);
	}

	@Test
	void allStrikes() {

		var resource = this.getClass().getClassLoader().getResourceAsStream("gameinputs/all-strikes.txt");
		assert resource != null;

		new GamePlayResourceReader(new GameControllerFactory()).play(resource, BOWLING);
	}

	@Test
	void allFouls() {

		var resource = this.getClass().getClassLoader().getResourceAsStream("gameinputs/all-fouls.txt");
		assert resource != null;

		new GamePlayResourceReader(new GameControllerFactory()).play(resource, BOWLING);
	}

	@Test
	void allZero() {

		var resource = this.getClass().getClassLoader().getResourceAsStream("gameinputs/all-zero.txt");
		assert resource != null;

		new GamePlayResourceReader(new GameControllerFactory()).play(resource, BOWLING);
	}
}