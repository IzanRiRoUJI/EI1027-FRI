package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OfferDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addOffer(Offer offer) {
        jdbcTemplate.update("INSERT INTO Offer VALUES (?,?,?,?,?,?)",
                offer.getName(), offer.getDniOffer(), offer.getSkillId(),
                offer.getDescription(), offer.getStartDate(), offer.getEndDate());
    }

    public void deleteOffer(Offer offer) {
        jdbcTemplate.update("DELETE FROM Offer WHERE dniOffer = ? AND skillId = ?",
                offer.getDniOffer(), offer.getSkillId());
    }

    public void deleteOffer(String dniOffer) {
        jdbcTemplate.update("DELETE FROM Offer WHERE dniOffer = ?",
                dniOffer);
    }

    public void updateOffer(Offer offer) {
        jdbcTemplate.update("UPDATE Offer " +
                        "SET name=?, description=?, starDate=?, endDate=?" +
                        "WHERE dniOffer=? AND skillId=?",
                offer.getName(), offer.getDescription(), offer.getStartDate(),
                offer.getEndDate(), offer.getDniOffer(), offer.getSkillId());
    }

    public Offer getOffer(String dniOffer) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Offer WHERE dniOffer=?",
                    new OfferRowMapper(), dniOffer);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Offer> getOffers() {
        try {
            return jdbcTemplate.query("SELECT * FROM Offer", new OfferRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Offer>();
        }
    }
}