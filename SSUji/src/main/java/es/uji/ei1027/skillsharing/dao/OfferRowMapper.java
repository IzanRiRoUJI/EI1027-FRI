package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

public final class OfferRowMapper implements RowMapper<Offer> {

    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setName(rs.getString("name"));
        offer.setDniOffer(rs.getString("dniOffer"));
        offer.setSkillId(rs.getInt("skillId"));
        offer.setDescription(rs.getString("description"));

        Date t = rs.getDate("startDate");
        offer.setStartDate(t != null ? t.toLocalDate() : null);

        t = rs.getDate("endDate");
        offer.setEndDate(t != null ? t.toLocalDate() : null);

        return offer;

//                name        VARCHAR(50),
//                dniOffer    VARCHAR(10),
//                skillId     SERIAL,
//                description VARCHAR(100),
//                startDate   DATE,
//                endDate     DATE,
    }
}
