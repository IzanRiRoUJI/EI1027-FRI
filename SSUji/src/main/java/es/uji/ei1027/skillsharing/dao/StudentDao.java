package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addStudent(Student student) {
        jdbcTemplate.update("INSERT INTO Student VALUES (?,?,?,?,?,?)",
                student.getDni(), student.getName(), student.getEmail(),
                student.getDegree(), student.getBalance(), student.isSKP());
    }

    public void deleteStudent(Student student) {
        jdbcTemplate.update("DELETE FROM Student WHERE dni = ?",
                student.getDni());
    }

    public void deleteStudent(String dni) {
        jdbcTemplate.update("DELETE FROM Student WHERE dni = ?",
                dni);
    }

    public void updateStudent(Student student) {
        jdbcTemplate.update("UPDATE Student " +
                "SET name=?, email=?, degree=?, balance=?, isSKP=?" +
                "WHERE dni=?",
                student.getName(), student.getEmail(), student.getDegree(),
                student.getBalance(), student.isSKP(), student.getDni());
    }

    public Student getStudent(String dniStudent) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Student WHERE dni=?",
                    new StudentRowMapper(), dniStudent);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Student> getStudents() {
        try {
            return jdbcTemplate.query("SELECT * FROM Student", new StudentRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Student>();
        }
    }

    public List<Student> getStudentsByBanStatus(boolean banStatus) {
        try {
            return jdbcTemplate.query("SELECT * FROM Student WHERE isSKP=?", new StudentRowMapper(), banStatus);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Student>();
        }
    }

}
