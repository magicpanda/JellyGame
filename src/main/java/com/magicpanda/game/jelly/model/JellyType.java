package com.magicpanda.game.jelly.model;

import com.magicpanda.game.jelly.util.Utilities;

/**
 * Created by 利彬 on 2015/3/29.
 */
public enum JellyType {
    NULL('N'),
    BEAN('B'),
    HORIZONTAL('H'),
    VERTICAL('V'),
    SPLASH('S');

    private char symbol;
    JellyType(char symbol){
        this.symbol = symbol;
    }
    public char getJellyType() {
        return this.symbol;
    }

    public static JellyType getJelly(char type) {
        JellyType[] values = JellyType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getJellyType() == type) {
                return values[i];
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return symbol+"";
    }
    public static JellyType getRandomJellyType(){
        int random = Utilities.getRandomNumberFrom(1, 4);
        return JellyType.values()[random];
    }
}
