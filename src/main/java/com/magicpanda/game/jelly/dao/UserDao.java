package com.magicpanda.game.jelly.dao;

import com.magicpanda.game.jelly.domain.User;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by 利彬 on 2015/3/29.
 * User Data and Current progress Data and etc.DAO
 */
@Component
public class UserDao extends JdbcDaoSupport {

    public Collection<User> findUserBySessionId(String sessionId) {
        String sql = "SELECT * FROM user t where sessionId = '" + StringEscapeUtils.escapeSql(sessionId) + "'";
        return super.getJdbcTemplate().query(sql, new RowMapper() {
            public Object mapRow(ResultSet rs, int num) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setDescription(rs.getString("description"));
                user.setInstallTime(rs.getLong("install_time"));
                user.setSessionId(rs.getString("sessionId"));
                user.setCurrentLevel(rs.getInt("currentLevel"));
                user.setCurrentLayout(rs.getString("currentLayout"));
                user.setCurrentStep(rs.getInt("currentStep"));
                return user;
            }
        });
    }

    public int updateUserBySessionId(final String sessionId, final String layout) {
        String sql = "UPDATE user t set currentLayout = ? where sessionId = ?";
        return getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, layout);
                ps.setString(2, sessionId);
            }
        });
    }

    public int saveUserBySessionId(final String sessionId, final int level, final String layout) {
        String sql = "INSERT INTO user (sessionId, currentLevel, currentLayout) VALUES (?, ?, ?)";
        int update = getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, sessionId);
                ps.setInt(2, level);
                ps.setString(3, layout);
            }
        });
        return update;
    }
}
