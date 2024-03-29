package es.uji.ei1027.skillsharing.dao;


import es.uji.ei1027.skillsharing.model.Collaboration;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

public final class CollaborationRowMapper implements RowMapper<Collaboration> {

    public Collaboration mapRow(ResultSet rs, int rowNum) throws SQLException {
        Collaboration collaboration = new Collaboration();
        collaboration.setId(rs.getInt("id"));
        collaboration.setIdOffer(rs.getInt("idOffer"));
        collaboration.setIdRequest(rs.getInt("idRequest"));
        collaboration.setSkillId(rs.getInt("skillId"));
        collaboration.setPlace(rs.getString("place"));
        collaboration.setState(rs.getString("state"));
        collaboration.setScore(rs.getInt("score"));
        collaboration.setHours(rs.getFloat("hours"));
        collaboration.setStartDate(rs.getObject("startDate", LocalDate.class));
        collaboration.setEndDate(rs.getObject("endDate", LocalDate.class));
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
