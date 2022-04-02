package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SkillDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

//    id			SERIAL,
//    name        VARCHAR(50),
//    description VARCHAR(100),
//    level       VARCHAR(50),
//    active      BOOLEAN DEFAULT TRUE,

    public void addSkill(Skill skill){
        jdbcTemplate.update(
                "INSERT INTO Skill VALUES(?, ?, ?, ?, ?)",
                skill.getId(), skill.getName(), skill.getDescription(), skill.getLevel(), skill.isActive());
    }


    public void deleteSkill(int id){
        jdbcTemplate.update(
                "DELETE FROM Skill WHERE id=?", id);
    }

    public void updateSkill(Skill skill){
        jdbcTemplate.update(
                "UPDATE Skill SET name=?, description=?, level=?, active=?" +
                                "WHERE id=?",
                skill.getName(), skill.getDescription(), skill.getLevel(), skill.isActive(), skill.getId());
    }

    public Skill getSkill(int id){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Skill WHERE id=?", new SkillRowMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* devuelve todas las habilidades como objetos en una lista */
    public List<Skill> getSkills(){
        try {
            return jdbcTemplate.query("SELECT * FROM Skill", new SkillRowMapper());

        }catch(EmptyResultDataAccessException e) {
            return new ArrayList<Skill>();
        }
    }
}
