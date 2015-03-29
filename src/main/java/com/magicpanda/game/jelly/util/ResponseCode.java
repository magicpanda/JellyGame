package com.magicpanda.game.jelly.util;


/**
 * An enumerated list of all the response codes that the server can send
 * to an RPC client.
 * <p/>
 */
public enum ResponseCode {
    SUCCESSED(0),
    MOVE_JELLY_INVALID(1),

    UNKNOWN_ERROR(1000);

    private final int intValue;

    private ResponseCode(int value) {
        this.intValue = value;
    }

    public String getResponseName() {
        return this.name();
    }

    public int getResponseValue() {
        return intValue;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s > %s (%d)]",
                getClass().getSimpleName(), name(), intValue);
    }
}