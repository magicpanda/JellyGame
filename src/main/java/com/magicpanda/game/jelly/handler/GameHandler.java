package com.magicpanda.game.jelly.handler;

import com.magicpanda.game.jelly.model.JellyType;
import com.magicpanda.game.jelly.model.Score;
import com.magicpanda.game.jelly.util.GamePreConditions;
import com.magicpanda.game.jelly.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.magicpanda.game.jelly.util.ResponseCode.MOVE_JELLY_INVALID;
import static com.magicpanda.game.jelly.util.Utilities.*;

/**
 * Created by 利彬 on 2015/3/29.
 */
@Component
public class GameHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameHandler.class);

    public String moveLayout(String layout, int row0, int col0, int row1, int col1) {
        GamePreConditions.checkState(valideRowRange(row0) && valideRowRange(row1) && valideColumnRange(col0) && valideColumnRange(col1),
                MOVE_JELLY_INVALID, "INVALID PARAMS");
        JellyType[][] jellyLayouts = Utilities.convertStringToLayout(layout);
        moveAndAction(jellyLayouts, row0, col0, row1, col1);
        return fillJellyAfterMove(jellyLayouts);
    }

    private void moveAndAction(JellyType[][] jellyLayouts, int row0, int col0, int row1, int col1) {
        GamePreConditions.checkState(row0 <= row1 && col0 <= col1, MOVE_JELLY_INVALID, "INVALID PARAMS");
        List<Position> effectPositions = new ArrayList<>();
        for (int i = col0; i <= col1; i++) {
            for (int j = row0; j <= row1; j++) {
                effectPositions.add(new Position(i, j));
            }
        }
        Score score = new Score();
        clearJelly(jellyLayouts, effectPositions, score);
        LOGGER.debug("Total erase count is :" + score.getCount());
    }

    private void clearJelly(JellyType[][] jellys, List<Position> effectPositions, Score score) {
        if (effectPositions.size() == 0) return;
        List<Position> newEffectPositions = new ArrayList<>();
        for (Position p : effectPositions) {
            if (!Utilities.valideColumnRange(p.x) || !Utilities.valideRowRange(p.y) ){
                continue;
            }
            JellyType jellyType = jellys[p.y][p.x];
            if (jellyType == JellyType.NULL) {
                continue;
            } else {
                LOGGER.error("Set positon null x:{}, y:{}", p.x, p.y);
                switch (jellyType) {
                    case NULL:
                        break;
                    case BEAN:
                        break;
                    case HORIZONTAL:
                        for (int i = 0; i < Utilities.JELLY_COLUMN; i++) {
                            newEffectPositions.add(new Position(i, p.y));
                        }
                        break;
                    case VERTICAL:
                        for (int i = 0; i < Utilities.JELLY_ROW; i++) {
                            newEffectPositions.add(new Position(p.x, i));
                        }
                        break;
                    case SPLASH:
                        newEffectPositions.add(new Position(p.x - 1, p.y - 1));
                        newEffectPositions.add(new Position(p.x - 1, p.y));
                        newEffectPositions.add(new Position(p.x - 1, p.y + 1));
                        newEffectPositions.add(new Position(p.x, p.y - 1));
                        newEffectPositions.add(new Position(p.x, p.y + 1));
                        newEffectPositions.add(new Position(p.x + 1, p.y - 1));
                        newEffectPositions.add(new Position(p.x + 1, p.y));
                        newEffectPositions.add(new Position(p.x + 1, p.y + 1));
                        break;
                }
                jellys[p.y][p.x] = JellyType.NULL;
                score.incCount();
            }
        }
        clearJelly(jellys, newEffectPositions, score);
    }

    private String fillJellyAfterMove(JellyType[][] jellyLayouts) {
        for(int col = 0; col < Utilities.JELLY_COLUMN;col++){
            List<JellyType> jellys = new ArrayList<>();
            for(int row = Utilities.JELLY_ROW - 1;row >= 0; row--){
                if(jellyLayouts[row][col] != JellyType.NULL){
                  jellys.add(jellyLayouts[row][col]);
                }
            }
            int count = 0;
            for(int row = Utilities.JELLY_ROW - 1; row >= 0; row--){
                if(count >= jellys.size()) {
                    jellyLayouts[row][col] = JellyType.getRandomJellyType();
                } else {
                    jellyLayouts[row][col] = jellys.get(count);
                }
                count++;
            }
        }
        return convertLayoutToString(jellyLayouts);
    }

    public static void main(String args[]) {
        String layout = "BBBBBBBH-BBBBVBBB-VBBBBHBB-BBBBBBBB-BBBBBBBB-BBBBBBBB-BBBBSBBB-BBBBBBBB";
        GameHandler gameHandler = new GameHandler();
        JellyType[][] jellyLayouts = Utilities.convertStringToLayout(layout);
        gameHandler.moveAndAction(jellyLayouts, 0, 1, 3, 5);
        String layoutAfter = Utilities.convertLayoutToString(jellyLayouts);
        String fillJellyAfterMove = gameHandler.fillJellyAfterMove(jellyLayouts);
    }
}
