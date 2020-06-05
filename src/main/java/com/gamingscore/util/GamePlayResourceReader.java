package com.gamingscore.util;

import com.gamingscore.controllers.GameControllerFactory;
import com.gamingscore.game.RoundBasedGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

public class GamePlayResourceReader {

	private final GameControllerFactory gameControllerFactory;

	public GamePlayResourceReader(final GameControllerFactory gameControllerFactory) {
		this.gameControllerFactory = gameControllerFactory;
	}

	public void play(final InputStream resource, final RoundBasedGame game) {

		final var lineIdx = new AtomicInteger(1);
		final var gameController = gameControllerFactory.getGameController(game);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {

			reader.lines()
			.map(inputRow -> gameController.checkInput(lineIdx.getAndIncrement(), inputRow))
			.map(gameController::toGameRound)
			.forEach(gameController::playRound);

			gameController.finish();
		}
		catch (IOException e) {
			throw new RuntimeException("Unable to read file input", e);
		}

		gameController.disployResults();
	}
}
