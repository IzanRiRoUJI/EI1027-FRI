package dao;

import model.Offer;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public final class OfferRowMapper implements RowMapper<Offer> {

    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setName(rs.getString("name"));
        offer.setDniOffer(rs.getString("dniOffer"));
        offer.setSkillId(rs.getInt("skillId"));
        offer.setDescription(rs.getString("description"));

        Time t = rs.getTime("startDate");
        offer.setStartDate(t != null ? t.toLocalTime() : null);

        t = rs.getTime("endDate");
        offer.setEndDate(t != null ? t.toLocalTime() : null);

        return offer;

//                name        VARCHAR(50),
//                dniOffer    VARCHAR(10),
//                skillId     SERIAL,
//                description VARCHAR(100),
//                startDate   DATE,
//                endDate     DATE,
    }
}
