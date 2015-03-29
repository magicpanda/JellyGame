package com.magicpanda.game.jelly.exception;

import com.magicpanda.game.jelly.util.ResponseCode;

public class GameRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final int code;

    public GameRuntimeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public GameRuntimeException(String message) {
        super(message);
        this.code = ResponseCode.UNKNOWN_ERROR.getResponseValue();
    }
}
