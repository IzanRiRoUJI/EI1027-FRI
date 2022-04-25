package es.uji.ei1027.skillsharing.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.uji.ei1027.skillsharing.model.Student;
import org.springframework.jdbc.core.RowMapper;

public final class StudentRowMapper implements RowMapper<Student> {

    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setDni(rs.getString("dni"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setDegree(rs.getString("degree"));
        student.setBalance(rs.getFloat("balance"));
        student.setSKP(rs.getBoolean("isSKP"));
        student.setBanned(rs.getBoolean("banned"));

        return student;
    }
}
