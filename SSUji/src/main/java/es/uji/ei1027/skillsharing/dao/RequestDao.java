package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Offer;
import es.uji.ei1027.skillsharing.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
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
//        jdbcTemplate.update("INSERT INTO Request VALUES (?,?,?,?,?,?)",
//                request.getName(), request.getDniRequest(), request.getSkillId(),
//                request.getDescription(), request.getStartDate(), request.getEndDate());

        jdbcTemplate.update("INSERT INTO Request (name, dniRequest, skillId, description, startDate, endDate) " +
                        "VALUES (?,?,?,?,?,?)",
                request.getName(), request.getDniRequest(), request.getSkillId(),
                request.getDescription(), request.getStartDate(), request.getEndDate());
    }

//    public void deleteRequest(Request request) {
//        jdbcTemplate.update("DELETE FROM Request WHERE dniRequest = ? AND skillId = ?",
//                request.getDniRequest(), request.getSkillId());
//    }

    public void deleteRequest(Request request) {
        jdbcTemplate.update("DELETE FROM Request WHERE id = ?", request.getSkillId());
    }

    public void deleteRequest(int id) {
        jdbcTemplate.update("DELETE FROM Request WHERE id = ?", id);
    }

//    public void updateRequest(Request request) {
//        jdbcTemplate.update("UPDATE Request " +
//                        "SET name=?, description=?, startDate=?, endDate=?" +
//                        "WHERE dniRequest=? AND skillId=?",
//                request.getName(), request.getDescription(), request.getStartDate(),
//                request.getEndDate(), request.getDniRequest(), request.getSkillId());
//    }

    public void deleteBySetFinishDate(int id) {
        jdbcTemplate.update("UPDATE Request SET endDate=? WHERE id = ?", LocalDate.now(), id);
    }


    public void updateRequest(Request request) {
        jdbcTemplate.update("UPDATE Request " +
                        "SET name=?, description=?, startDate=?, endDate=?, dniRequest=?, skillId=?" +
                        "WHERE id = ?",
                request.getName(), request.getDescription(), request.getStartDate(),
                request.getEndDate(), request.getDniRequest(), request.getSkillId(), request.getId());
    }

//    public Request getRequest(String dniRequest, int skillId) {
//        try {
//            return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE dniRequest=? AND skillId = ?",
//                    new RequestRowMapper(), dniRequest, skillId);
//        }
//        catch(EmptyResultDataAccessException e) {
//            return null;
//        }
//    }

    public Request getRequest(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE id=?",
                    new RequestRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Request> getRequestsUnexpired() {
        try {
            return jdbcTemplate.query("SELECT * FROM Request WHERE DATE(endDate) > DATE(NOW())", new RequestRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
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

    public List<Request> getRequestsBySkill(int idSkill) {
        try {
            return jdbcTemplate.query("SELECT * FROM Request WHERE skillId=?", new RequestRowMapper(), idSkill);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }


    public List<Request> getMyActiveRequests(String dni) {
        try {
            return jdbcTemplate.query("SELECT * FROM Request WHERE DATE(endDate) > DATE(NOW()) and dniRequest = ?", new RequestRowMapper(), dni);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }

    public List<Request> getMyInactiveRequests(String dni) {
        try {
            return jdbcTemplate.query("SELECT * FROM Request WHERE DATE(endDate) <= DATE(NOW()) and dniRequest = ?", new RequestRowMapper(), dni);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }
}
