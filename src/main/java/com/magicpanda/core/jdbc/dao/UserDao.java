package com.magicpanda.core.jdbc.dao;

import com.magicpanda.game.jelly.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@SuppressWarnings("all")
@Component
public class UserDao extends JdbcDaoSupport {

	public Collection<User> findUserBySessionId(String sessionId) {
		String sql = "SELECT * FROM user t where sessionId = '" + sessionId + "'";
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

    public int updateUserBySessionId(String sessionId, String layout) {
        String sql = "UPDATE user t set currentLayout = '" + layout + "' where sessionId = '" + sessionId + "'";
        return getJdbcTemplate().update(sql);
    }

    public int saveUserBySessionId(String sessionId, int level, String layout) {
        String sql = "INSERT INTO user (sessionId, currentLevel, currentLayout) VALUES ('"+ sessionId + "'," + level + ",'" + layout + "')";
        int update = getJdbcTemplate().update(sql);
        return update;
    }
}
