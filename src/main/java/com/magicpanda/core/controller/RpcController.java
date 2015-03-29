
package com.magicpanda.core.controller;

import com.magicpanda.core.jdbc.dao.UserDao;
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
import java.util.Map;

@Controller
public final class RpcController implements InitializingBean {
    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(RpcController.class);
    private final boolean developMode = true;
    private UserDao userDao;
    private CacheManager cacheManager;
    private GameHandler gameHandler;

    @Autowired
    public RpcController(UserDao userDao, CacheManager cacheManager, GameHandler gameHandler) {
        this.userDao = userDao;
        this.cacheManager = cacheManager;
        this.gameHandler = gameHandler;
    }

    @RequestMapping(value = "/start-level", method = {RequestMethod.POST, RequestMethod.GET}, produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String startlevel(@RequestParam("level") String level,
                             HttpServletRequest request) {
        LevelLayout levelLayout;
        String sessionId;
        try {
            Map<Integer, LevelLayout> levelLayoutCache = cacheManager.getLevelLayoutCache(Integer.parseInt(level));
            levelLayout = levelLayoutCache.get(Integer.parseInt(level));
            sessionId = Utilities.generateKey();
            userDao.saveUserBySessionId(sessionId, levelLayout.getLevel(), levelLayout.getLayout());
        }catch (Exception e){
            e.printStackTrace();
            return "INVALID PARAMS";
        }
        return sessionId + "\r\n" + levelLayout.getLayout().replace("-","\r\n");
    }

    @RequestMapping(value = "/move", method = {RequestMethod.POST, RequestMethod.GET}, produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String move(@RequestParam("sessionId") String sessionId, @RequestParam("row0") int row0,
                       @RequestParam("col0") int col0, @RequestParam("row1") int row1,
                       @RequestParam("col1") int col1, HttpServletRequest request) {
        String layout;
        User user = userDao.findUserBySessionId(sessionId).iterator().next();
        try {
            layout = gameHandler.moveLayout(user.getCurrentLayout(), row0, col0, row1, col1);
            userDao.updateUserBySessionId(sessionId, layout);
        }catch (Exception e){
            e.printStackTrace();
            return "INVALID PARAMS";
        }
        return layout.replace("-", "\r\n");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
