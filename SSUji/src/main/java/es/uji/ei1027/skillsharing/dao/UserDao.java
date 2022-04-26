package es.uji.ei1027.skillsharing.dao;

import es.uji.ei1027.skillsharing.model.Student;

public interface UserDao {
    Student loadUserByUsername(String email, String password);
}
