package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Request;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public final class RequestRowMapper implements RowMapper<Request> {

    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request request = new Request();
        request.setName(rs.getString("name"));
        request.setDniRequest(rs.getString("dniRequest"));
        request.setSkillId(rs.getInt("skillId"));
        request.setDescription(rs.getString("description"));

        Time t = rs.getTime("startDate");
        request.setStartDate(t != null ? t.toLocalTime() : null);

        t = rs.getTime("endDate");
        request.setEndDate(t != null ? t.toLocalTime() : null);

        return request;

//                name        VARCHAR(50),
//                dniRequest  VARCHAR(10),
//                skillId     SERIAL,
//                description VARCHAR(100),
//                startDate   DATE,
//                endDate     DATE,
    }
}
