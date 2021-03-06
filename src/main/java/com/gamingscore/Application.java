package com.gamingscore;

import com.gamingscore.controllers.GameControllerFactory;
import com.gamingscore.util.GamePlayResourceReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.gamingscore.game.RoundBasedGame.BOWLING;

public class Application {

    public static void main(String[] args) {

        if (args == null || args.length != 1) {
            throw new IllegalArgumentException("Text file input argument is missing");
        }

        File file = new File(args[0]);

        var gamePlayResourceReader = new GamePlayResourceReader(new GameControllerFactory());

        try {
            var resource = new FileInputStream(file);
            gamePlayResourceReader.play(resource, BOWLING);
        }
        catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Text file input argument is invalid. File "+ file + " not found", e);
        }
    }
}