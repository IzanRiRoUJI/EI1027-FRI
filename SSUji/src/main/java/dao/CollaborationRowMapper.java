package dao;


import model.Collaboration;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public final class CollaborationRowMapper implements RowMapper<Collaboration> {

    public Collaboration mapRow(ResultSet rs, int rowNum) throws SQLException {
        Collaboration collaboration = new Collaboration();
        collaboration.setDniOffer(rs.getString("dniOffer"));
        collaboration.setDniRequest(rs.getString("dniRequest"));

        collaboration.setSkillId(rs.getInt("skillId"));
        collaboration.setPlace(rs.getString("place"));
        collaboration.setState(rs.getString("state"));
        collaboration.setScore(rs.getInt("score"));
        collaboration.setHours(rs.getFloat("hours"));

        Time t = rs.getTime("startDate");
        collaboration.setStartDate(t != null ? t.toLocalTime() : null);

        t = rs.getTime("endDate");
        collaboration.setEndDate(t != null ? t.toLocalTime() : null);

        return collaboration;

//                dniOffer    VARCHAR(10),
//                dniRequest  VARCHAR(10),
//                skillId     SERIAL,
//                place       VARCHAR(100),
//                state       VARCHAR(50) DEFAULT 'notStarted',
//                score       INTEGER,
//                hours       FLOAT,
//                startDate   DATE,
//                endDate     DATE,
    }
}
