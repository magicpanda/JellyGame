package com.magicpanda.game.jelly.model;

/**
 * Created by 利彬 on 2015/3/28.
 * Jelly Init Layout in a level.
 */

public class LevelLayout {
    int level;
    String layout;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LevelLayout)) return false;

        LevelLayout that = (LevelLayout) o;

        if (level != that.level) return false;
        if (!layout.equals(that.layout)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level;
        result = 31 * result + layout.hashCode();
        return result;
    }
}
