package com.magicpanda.game.util;

import com.magicpanda.game.jelly.model.JellyType;
import com.magicpanda.game.jelly.util.Utilities;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 利彬 on 2015/3/30.
 */
public class UtilitiesTest {

    @Test
    public void TestStringAndLayoutConverter() {
        String layout = "BBBBBBBH-SBBBBBBB-VBBBBBBB-BBBBBBBB-BBBBBBBB-BBBBBBBB-BBBBBBBB-BBBBBBBB";
        JellyType[][] jellys = Utilities.convertStringToLayout(layout);
        String layoutAfter = Utilities.convertLayoutToString(jellys);
        Assert.assertEquals(layout, layoutAfter);
    }
}
