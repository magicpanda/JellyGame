package com.magicpanda.core.jdbc.dao;

import com.magicpanda.game.jelly.model.LevelLayout;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class LevelDao extends JdbcDaoSupport {

    public List<LevelLayout> getLevelLayoutList() {
        String sql = "SELECT * FROM level_layout t";
        return super.getJdbcTemplate().query(sql, new RowMapper() {
            public LevelLayout mapRow(ResultSet rs, int num) throws SQLException {
                LevelLayout levelLayout = new LevelLayout();
                levelLayout.setLevel(rs.getInt("level"));
                levelLayout.setLayout(rs.getString("layout"));
                return levelLayout;
            }
        });
    }
}
