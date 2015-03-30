package com.magicpanda.core.controller;

import com.magicpanda.game.jelly.dao.UserDao;
import com.magicpanda.game.jelly.domain.User;
import com.magicpanda.game.jelly.handler.GameHandler;
import com.magicpanda.game.jelly.manager.CacheManager;
import com.magicpanda.game.jelly.model.LevelLayout;
import com.magicpanda.game.jelly.util.Utilities;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;
/**
 * Created by 利彬 on 2015/3/29.
 * Game web controller for http request
 */
@Controller
public final class RpcController implements InitializingBean {
    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(RpcController.class);
    private UserDao userDao;
    private CacheManager cacheManager;
    private GameHandler gameHandler;

    @Autowired
    public RpcController(UserDao userDao, CacheManager cacheManager, GameHandler gameHandler) {
        this.userDao = userDao;
        this.cacheManager = cacheManager;
        this.gameHandler = gameHandler;
    }

    @RequestMapping(value = "/auth", method = {RequestMethod.GET}, produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String auth(@RequestParam("user") String level, @RequestParam("pass") String pass,
                       HttpServletRequest request) {
        //TODO auth
        return "auth";
    }

    @RequestMapping(value = "/start-level", method = {RequestMethod.GET}, produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String startlevel(@RequestParam("level") String level) {
        LevelLayout levelLayout;
        String sessionId;
        try {
            Map<Integer, LevelLayout> levelLayoutCache = cacheManager.getLevelLayoutCache(Integer.parseInt(level));
            levelLayout = levelLayoutCache.get(Integer.parseInt(level));
            sessionId = Utilities.generateKey();
            userDao.saveUserBySessionId(sessionId, levelLayout.getLevel(), levelLayout.getLayout());
        } catch (Exception e) {
            LOGGER.error("Error occur when start level {}", e.getCause());
            return "INVALID PARAMS";
        }
        LOGGER.debug("Start Level {} and sessionId is {}", level, sessionId);
        return sessionId + "\r\n" + levelLayout.getLayout().replace("-", "\r\n");
    }

    @RequestMapping(value = "/move", method = {RequestMethod.GET}, produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String move(@RequestParam("sessionId") String sessionId, @RequestParam("row0") int row0,
                       @RequestParam("col0") int col0, @RequestParam("row1") int row1,
                       @RequestParam("col1") int col1) {
        Iterator<User> iterator = userDao.findUserBySessionId(sessionId).iterator();
        if (!iterator.hasNext()) {
            return "INVALID PARAMS";
        }
        String layout;
        User user = iterator.next();
        try {
            layout = gameHandler.moveLayout(user.getCurrentLayout(), row0, col0, row1, col1);
            userDao.updateUserBySessionId(sessionId, layout);
        } catch (Exception e) {
            LOGGER.error("Error {} occur when move jelly in session {}", e.getCause(), sessionId);
            return "INVALID PARAMS";
        }
        return layout.replace("-", "\r\n");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
