package com.magicpanda.game.jelly.util;

import com.magicpanda.game.jelly.exception.GameRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class GamePreConditions {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(GamePreConditions.class);

    private GamePreConditions() {
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException("Game General Error.");
        }
    }

    public static void checkState(boolean expression,
                                  GameRuntimeException exception) {
        if (!expression) {
            LOGGER.error(exception.getMessage());
            throw exception;
        }
    }


    public static void checkState(boolean expression,
                                  String message) {
        if (!expression) {
            LOGGER.error(message);
            throw new GameRuntimeException(message);
        }
    }

    public static void checkState(boolean expression, ResponseCode code,
                                  String message) {
        if (!expression) {
            LOGGER.error(message);
            throw new GameRuntimeException(code.getResponseValue(), message);
        }
    }
}
