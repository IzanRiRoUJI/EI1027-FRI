package dao;

import model.Skill;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public final class SkillRowMapper implements RowMapper<Skill> {

    public Skill mapRow(ResultSet rs, int rowNum) throws SQLException {
        Skill skill = new Skill();
        skill.setId(rs.getInt("id"));
        skill.setName(rs.getString("name"));
        skill.setDescription(rs.getString("description"));
        skill.setLevel(rs.getString("level"));
        skill.setActive(rs.getBoolean("active"));
        return skill;

//      id			SERIAL,
//      name        VARCHAR(50),
//      description VARCHAR(100),
//      level       VARCHAR(50),
//      active      BOOLEAN DEFAULT TRUE,
    }
}
