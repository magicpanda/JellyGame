package com.magicpanda.game.jelly.manager;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.magicpanda.core.jdbc.dao.LevelDao;
import com.magicpanda.game.jelly.model.LevelLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by 利彬 on 2015/3/28.
 * Cache Data for frequently used data
 */
@Component
public class CacheManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheManager.class);
    public static final int MAX_LEVEL_LAYOUT_SIZE = 1000;
    private int cacheExpire = 100;//In Second
    private LevelDao levelDao;
    //Level Layout Cache which refresh in 100 second and max size is 1000
    private LoadingCache<Integer, Map<Integer, LevelLayout>> levelLayoutCache = CacheBuilder.newBuilder().expireAfterAccess(cacheExpire, TimeUnit.SECONDS).maximumSize(MAX_LEVEL_LAYOUT_SIZE).build(new LevelLayoutCacheLoader());

    @Autowired
    public CacheManager(LevelDao levelDao) {
        this.levelDao = levelDao;
    }

    public Map<Integer, LevelLayout> getLevelLayoutCache(Integer key) {
        Map<Integer, LevelLayout> result = new HashMap<>();
        try {
            result = levelLayoutCache.get(key);
        } catch (ExecutionException e) {
            LOGGER.error("Error occur when getLevelLayoutCache {}", e.getCause());
        }
        return result;
    }

    private class LevelLayoutCacheLoader extends CacheLoader<Integer, Map<Integer, LevelLayout>> {

        @Override
        public Map<Integer, LevelLayout> load(Integer key) throws Exception {
            Map<Integer, LevelLayout> result = new HashMap<>();
            for (LevelLayout levelLayout : levelDao.getLevelLayoutList()) {
                result.put(levelLayout.getLevel(), levelLayout);
            }
            return result;
        }
    }
}
