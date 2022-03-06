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
        System.out.println("------------" + student.isSKP());
        System.out.println("------------" + rs.getBoolean("isSKP"));

        return student;
//                dni     VARCHAR(10),
//                name    VARCHAR(50),
//                email   VARCHAR(50),
//                degree  VARCHAR(50),
//                balance FLOAT DEFAULT 0,
//                isSKP   BOOLEAN DEFAULT FALSE,
    }
}
