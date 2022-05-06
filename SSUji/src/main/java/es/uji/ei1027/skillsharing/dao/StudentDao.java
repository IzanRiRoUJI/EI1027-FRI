package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Student;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDao implements UserDao{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addStudent(Student student) {
//        jdbcTemplate.update("INSERT INTO Student VALUES (?,?,?,?,?,?,?)",
//                student.getDni(), student.getName(), student.getEmail(),
//                student.getDegree(), student.getBalance(), student.isSKP(), student.getPassword());


        jdbcTemplate.update("INSERT INTO Student (dni, name, email, degree, password)" +
                        "VALUES (?,?,?,?,?)",
                student.getDni(), student.getName(), student.getEmail(), student.getDegree(), student.getPassword());
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
                "SET name=?, email=?, degree=?, balance=?, isSKP=?, password=?" +
                "WHERE dni=?",
                student.getName(), student.getEmail(), student.getDegree(),
                student.getBalance(), student.isSKP(), student.getDni(), student.getPassword());
    }

    public void updateStudentProfile(Student student) {
        jdbcTemplate.update("UPDATE Student " +
                        "SET name=?, email=?, degree=?" +
                        "WHERE dni=?",
                student.getName(), student.getEmail(), student.getDegree(), student.getDni());
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

    public Student getStudentByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Student WHERE email=?",
                    new StudentRowMapper(), email);
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
            return jdbcTemplate.query("SELECT * FROM Student WHERE banned=? AND isSKP=FALSE", new StudentRowMapper(), banStatus);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Student>();
        }
    }

    public void setBanStudent(String dni, boolean newStatus) {
        jdbcTemplate.update("UPDATE Student " +
                        "SET banned=? WHERE dni=?", newStatus, dni);
    }

    @java.lang.Override
    public Student loadUserByUsername(String email, String password) {

        System.out.println("email " + email + " | pass " + password);
        Student user = this.getStudentByEmail(email.trim());
        System.out.println(user);
        if (user == null)
            return null; // Usuari no trobat

        // Contrasenya
        if(password.equals(user.getPassword())){
            return user;
        } else{
            return null;
        }

//        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
//        if (passwordEncryptor.checkPassword(password, user.getPassword())) { //ERROR
//            // Es deuria esborrar de manera segura el camp password abans de tornar-lo
//            return user;
//        }
//        else {
//            return null; // bad login!
//        }
    }
}
