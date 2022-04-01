package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RequestDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addRequest(Request request) {
        jdbcTemplate.update("INSERT INTO Request VALUES (?,?,?,?,?,?)",
                request.getName(), request.getDniRequest(), request.getSkillId(),
                request.getDescription(), request.getStartDate(), request.getEndDate());
    }

    public void deleteRequest(Request request) {
        jdbcTemplate.update("DELETE FROM Request WHERE dniRequest = ? AND skillId = ?",
                request.getDniRequest(), request.getSkillId());
    }

    public void deleteRequest(String dniRequest, int skillId) {
        jdbcTemplate.update("DELETE FROM Request WHERE dniRequest = ? AND skillId = ?",
                dniRequest, skillId);
    }

    public void updateRequest(Request request) {
        jdbcTemplate.update("UPDATE Request " +
                        "SET name=?, description=?, starDate=?, endDate=?" +
                        "WHERE dniRequest=? AND skillId=?",
                request.getName(), request.getDescription(), request.getStartDate(),
                request.getEndDate(), request.getDniRequest(), request.getSkillId());
    }

    public Request getRequest(String dniRequest, int skillId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE dniRequest=? AND skillId = ?",
                    new RequestRowMapper(), dniRequest, skillId);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Request> getRequests() {
        try {
            return jdbcTemplate.query("SELECT * FROM Request", new RequestRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }
}
