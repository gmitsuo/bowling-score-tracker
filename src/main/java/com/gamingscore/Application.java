package com.gamingscore;

import com.gamingscore.controllers.GameControllerFactory;
import com.gamingscore.util.GamePlayResourceReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static com.gamingscore.game.RoundBasedGame.BOWLING;
import static java.nio.file.Files.newInputStream;

public class Application {

    public static void main(final String[] args) {

        if (args == null || args.length != 1) {
            throw new IllegalArgumentException("Text file input argument is missing");
        }

        final File file = new File(args[0]);

        final var gamePlayResourceReader = new GamePlayResourceReader(new GameControllerFactory());

        try {
            final var resource = newInputStream(Paths.get(file.getCanonicalPath()));
            gamePlayResourceReader.play(resource, BOWLING);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Text file input argument is invalid. File "+ file + " not found or corrupt", e);
        }
    }
}