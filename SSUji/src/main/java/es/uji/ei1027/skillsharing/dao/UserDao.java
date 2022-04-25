package es.uji.ei1027.skillsharing.dao;

public interface UserDao {
    Student loadUserByUsername(String email, String password);
}
