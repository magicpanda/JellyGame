package com.magicpanda.game.jelly.model;

/**
 * Created by 利彬 on 2015/3/29.
 * For Jelly Game score statistic
 */
public class Score {
    int count;
    int score;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incCount() {
        count++;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
