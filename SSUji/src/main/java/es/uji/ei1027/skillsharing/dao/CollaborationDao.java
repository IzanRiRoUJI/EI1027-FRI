package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Collaboration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CollaborationDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCollaboration(Collaboration collaboration){
        jdbcTemplate.update(
                "INSERT INTO Collaboration (idRequest, idOffer, skillId, place, state, score, hours, startDate, endDate) VALUES(? ,? ,? ,?, ?, ?, ?, ?, ?)",
                collaboration.getIdRequest(),collaboration.getIdOffer(),collaboration.getSkillId(), collaboration.getPlace(),
                collaboration.getState(), collaboration.getScore(), collaboration.getHours(), collaboration.getStartDate(), collaboration.getEndDate());
    }

    public void deleteCollaboration(Collaboration collaboration){
        jdbcTemplate.update(
                "DELETE FROM COLLABORATION WHERE id = ?",
                collaboration.getId());
    }

    public void deleteCollaboration(int id){
        jdbcTemplate.update(
                "DELETE FROM COLLABORATION WHERE id = ?", id);
    }

    public void updateCollaboration(Collaboration collaboration){
        jdbcTemplate.update(
                "UPDATE COLLABORATION SET place=?, score=?, hours=?, startDate=?, endDate=? WHERE id=?",
                collaboration.getPlace(),collaboration.getScore(), collaboration.getHours(), collaboration.getStartDate(), collaboration.getEndDate(),collaboration.getId());
    }

    public Collaboration getCollaboration(int id){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM COLLABORATION WHERE id = ?", new CollaborationRowMapper(),id);
        }catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Collaboration> getCollaborations(){
        try {
            return jdbcTemplate.query("SELECT * FROM Collaboration", new CollaborationRowMapper());
        }catch(EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }
    public List<Collaboration> getCollaborationsByState(String state){ //  (state in ('notStarted', 'inProgress', 'finished')),
        try {
            return jdbcTemplate.query("SELECT * FROM Collaboration WHERE state=?", new CollaborationRowMapper(), state);
        }catch(EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }

    public void setCollaborationState(Collaboration collaboration, String state){
        if(Objects.equals(collaboration.getState(), "notStarted"))
            jdbcTemplate.update("UPDATE Collaboration SET state=?, startDate=? WHERE id=?", state,LocalDate.now(),collaboration.getId());
        if(Objects.equals(collaboration.getState(), "inProgress"))
            jdbcTemplate.update("UPDATE Collaboration SET state=?, endDate=? WHERE id=?", state,LocalDate.now(),collaboration.getId());
    }
}

