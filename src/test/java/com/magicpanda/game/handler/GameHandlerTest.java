package com.magicpanda.game.handler;

import com.magicpanda.game.jelly.handler.GameHandler;
import com.magicpanda.game.jelly.model.JellyType;
import com.magicpanda.game.jelly.model.Score;
import com.magicpanda.game.jelly.util.Utilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 利彬 on 2015/3/29.
 * Test Jell Game Logic about jelly move, jelly break and random fill.
 */
public class GameHandlerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameHandlerTest.class);
    private GameHandler gameHandler;

    @Before
    public void setUp() throws Exception {
        gameHandler = new GameHandler();
    }

    @Test
    public void testMoveAndActionStep1() {
        String layout = "HBBBSBBB-BBBBBBBB-BSBBBBBB-BBBBVBBB-BHBBBBBB-SBBBBSBB-BBBBBBBB-BBBBBBBB";
        JellyType[][] jellyLayouts = Utilities.convertStringToLayout(layout);
        gameHandler.moveAndAction(jellyLayouts, 1, 0, 1, 2);
        String layoutAfterMove = Utilities.convertLayoutToString(jellyLayouts);
        Assert.assertEquals(layoutAfterMove, "HBBBSBBB-NNNBBBBB-BSBBBBBB-BBBBVBBB-BHBBBBBB-SBBBBSBB-BBBBBBBB-BBBBBBBB");
    }

    @Test
    public void testMoveAndActionScore() {
        String layout = "HBBBSBBB-BBBBBBBB-BSBBBBBB-BBBBVBBB-BHBBBBBB-SBBBBSBB-BBBBBBBB-BBBBBBBB";
        JellyType[][] jellyLayouts = Utilities.convertStringToLayout(layout);
        Score score = gameHandler.moveAndAction(jellyLayouts, 1, 0, 1, 5);
        String layoutAfterMove = Utilities.convertLayoutToString(jellyLayouts);
        Assert.assertEquals(layoutAfterMove, "HBBBSBBB-NNNNNNBB-BSBBBBBB-BBBBVBBB-BHBBBBBB-SBBBBSBB-BBBBBBBB-BBBBBBBB");
        Assert.assertEquals(score.getCount(), 6);
    }

    @Test
    public void testFillJellyAfterMoveStep1() {
        String layout = "HBBBSBBB-NNNBBBBB-BSBBBBBB-BBBBVBBB-BHBBBBBB-SBBBBSBB-BBBBBBBB-BBBBBBBB";
        JellyType[][] jellyLayouts = Utilities.convertStringToLayout(layout);
        String layoutAfterFill = gameHandler.fillJellyAfterMove(jellyLayouts);
        Assert.assertEquals(layoutAfterFill.substring(3, layoutAfterFill.length()), "BSBBB-HBBBBBBB-BSBBBBBB-BBBBVBBB-BHBBBBBB-SBBBBSBB-BBBBBBBB-BBBBBBBB");
    }

    @Test
    public void testMoveAndActionMoreComplex() {
        String layout = "BBBBBBBH-BBBBVBBB-VBBBBHBB-BBBBBBBB-BBBBBBBB-BBBBBBBB-BBBBSBBB-BBBBBBBB";
        JellyType[][] jellyLayouts = Utilities.convertStringToLayout(layout);
        gameHandler.moveAndAction(jellyLayouts, 0, 1, 3, 5);
        String layoutAfterMove = Utilities.convertLayoutToString(jellyLayouts);
        Assert.assertEquals(layoutAfterMove, "NNNNNNBH-NNNNNNBB-NNNNNNNN-NNNNNNBB-NBBBNBBB-NBBNNNBB-NBBNNNBB-NBBNNNBB");
    }
}
