
package com.magicpanda.game.jelly.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.magicpanda.game.jelly.model.JellyType;
import org.joda.time.DateTimeUtils;

import java.util.*;


public class Utilities {

    /**
     * Get a random number from given min to max, which maybe also equals to min or max.
     *
     * @param min - lower bound of the range
     * @param max - upper bound of the range
     * @return the random number
     */
    private static final Random random = new Random();
    public static final int JELLY_ROW = 8;
    public static final int JELLY_COLUMN = 8;

    public static int getRandomNumberFrom(int min, int max) {
        return random.nextInt((max + 1) - min) + min;
    }

    /**
     * Return a float number which is a random integer between min and max when divided by 100.
     * E.g getRandomFactor(90,  110) may return any value betwen 0.9 and 1.1
     *
     * @param min
     * @param max
     * @return
     */
    public static float getRandomFactor(int min, int max) {
        return (float) (random.nextInt((max + 1) - min) + min) / 100;
    }

    public static final String generateKey() {
        return Long.toString(DateTimeUtils.currentTimeMillis(), 36) + Integer.toString(random.nextInt(), 36);
    }

    public static final long currentTime() {
        return DateTimeUtils.currentTimeMillis();
    }

    /**
     * Return true or false based on a possibility.
     *
     * @return
     */
    public static boolean flippCoin(int possibility) {
        assert possibility > 0 && possibility < 100;
        return (random.nextInt(100) < possibility);
    }

    public static JellyType[][] convertStringToLayout(String layout){
        JellyType[][] layoutType = new JellyType[JELLY_ROW][JELLY_COLUMN];
        int lineCount = 0;
        for(String line : Splitter.on("-").split(layout)){
            int count = 0;
            for(char jellyType: line.toCharArray()){
                layoutType[lineCount][count] = JellyType.getJelly(jellyType);
                count++;
            }
            lineCount++;
        }
        return layoutType;
    }


    public static String convertLayoutToString(JellyType[][] layout){
        List<String> jellyRows = new ArrayList<>();
        for(JellyType[] jellRow : layout){
            jellyRows.add(Joiner.on("").join(jellRow));
        }
        return Joiner.on("-").join(jellyRows);
    }

    public static boolean valideRowRange(int pos){
        return pos >=0 && pos< JELLY_ROW;
    }

    public static boolean valideColumnRange(int pos){
        return pos >=0 && pos< JELLY_COLUMN;
    }
    public static void main(String args[]){
        String layout = "BBBBBBBH-SBBBBBBB-VBBBBBBB-BBBBBBBB-BBBBBBBB-BBBBBBBB-BBBBBBBB-BBBBBBBB";
        JellyType[][] jellys = convertStringToLayout(layout);
        String layoutAfter = convertLayoutToString(jellys);
        int random = getRandomNumberFrom(0, 4);
        while (random <= 4) {
            System.out.println(random);
            random = getRandomNumberFrom(0, 4);
        }
        System.out.println(layoutAfter);
    }
}
