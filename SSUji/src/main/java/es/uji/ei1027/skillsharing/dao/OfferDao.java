package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
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
//        jdbcTemplate.update("INSERT INTO Offer VALUES (?,?,?,?,?,?)",
//                offer.getName(), offer.getDniOffer(), offer.getSkillId(),
//                offer.getDescription(), offer.getStartDate(), offer.getEndDate());

        jdbcTemplate.update("INSERT INTO Offer (name, dniOffer, skillId, description, startDate, endDate) " +
                        "VALUES (?,?,?,?,?,?)",
                offer.getName(), offer.getDniOffer(), offer.getSkillId(),
                offer.getDescription(), offer.getStartDate(), offer.getEndDate());
    }

    public void deleteOffer(Offer offer) {
        jdbcTemplate.update("DELETE FROM Offer WHERE id = ?", offer.getId());
    }

//    public void deleteOffer(String dniOffer, int skillId) {
//        jdbcTemplate.update("DELETE FROM Offer WHERE dniOffer = ? AND skillId = ?",
//                dniOffer, skillId);
//    }

    public void deleteOffer(int id) {
        jdbcTemplate.update("DELETE FROM Offer WHERE id = ?", id);
    }

    public void deleteBySetFinishDate(int id) {
        System.out.println("Delete con id");
        jdbcTemplate.update("UPDATE Offer SET endDate=? WHERE id = ?", LocalDate.now(), id);
    }

    public void updateOffer(Offer offer) {
        jdbcTemplate.update("UPDATE Offer " +
                        "SET name=?, description=?, startDate=?, endDate=?, dniOffer=?, skillId=?" +
                        "WHERE id=?",
                offer.getName(), offer.getDescription(), offer.getStartDate(),
                offer.getEndDate(), offer.getDniOffer(), offer.getSkillId(), offer.getId());
    }


    public Offer getOffer(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Offer WHERE id=?",
                    new OfferRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Offer> getOffersUnexpired() {
        try {
            return jdbcTemplate.query("SELECT * FROM Offer WHERE DATE(endDate) > DATE(NOW())", new OfferRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Offer>();
        }
    }

    public List<Offer> getOffers() {
        try {
//            return jdbcTemplate.query("SELECT * FROM Offer WHERE DATE(endDate) > DATE(NOW())", new OfferRowMapper());
            return jdbcTemplate.query("SELECT * FROM Offer", new OfferRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Offer>();
        }
    }

    public List<Offer> getMyOffers(String dni) {
        try {
            return jdbcTemplate.query("SELECT * FROM Offer WHERE DATE(endDate) > DATE(NOW()) and dniOffer = ?", new OfferRowMapper(), dni);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Offer>();
        }
    }
}
