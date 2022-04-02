package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Collaboration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


public class CollaborationDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

//    private String dniOffer;
//    private String dniRequest;
//    private int skillId;
//    private String place;
//    private String state;
//    private int score;
//    private float hours;
//    private LocalTime startDate;
//    private LocalTime endDate;

    public void addCollaboration(Collaboration collaboration){
        jdbcTemplate.update(
                "INSERT INTO Collaboration VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                collaboration.getDniOffer(),collaboration.getDniRequest(), collaboration.getSkillId(),collaboration.getPlace(),
                collaboration.getState(), collaboration.getScore(), collaboration.getHours(), collaboration.getStartDate(), collaboration.getEndDate());
    }

    public void deleteCollaboration(Collaboration collaboration){
        jdbcTemplate.update(
                "DELETE FROM COLLABORATION WHERE dniOffer = ? AND dniRequest = ? AND skillId = ? AND state = ?",
                collaboration.getDniOffer(),collaboration.getDniRequest(), collaboration.getSkillId(), collaboration.getState());
    }

    public void deleteCollaboration(String dniOffer, String dniRequest, int skillId, String state){
        jdbcTemplate.update(
                "DELETE FROM COLLABORATION WHERE dniOffer = ? AND dniRequest = ? AND skillId = ? AND state = ?",
                dniOffer,dniRequest, skillId, state);
    }

    public void updateCollaboration(Collaboration collaboration){
        jdbcTemplate.update(
                "UPDATE FROM COLLABORATION SET place=?, score=?, hours=?, starDate=?, endDate=? WHERE dniOffer = ? AND dniRequest = ? AND skillId = ? AND state = ?",
                collaboration.getPlace(),collaboration.getScore(), collaboration.getHours(), collaboration.getStartDate(), collaboration.getEndDate(),collaboration.getDniOffer(),collaboration.getDniRequest(), collaboration.getSkillId(), collaboration.getState());
    }

    public Collaboration getCollaboration(String dniOffer,String dniRequest,int skillId, String state){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Skill WHERE dniOffer = ? AND dniRequest = ? AND skillId = ? AND state = ? ", new CollaborationRowMapper(),dniOffer,dniRequest, skillId, state);
        }catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    /* devuelve todas las habilidades como objetos en una lista */
    public List<Collaboration> getCollaborations(){
        try {
            return jdbcTemplate.query("SELECT * FROM Collaboration", new CollaborationRowMapper());
        }catch(EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }
}
