package es.uji.ei1027.skillsharing.model;

public class Student {
    private String dni;
    private String email;
    private String name;
    private String degree;
    private float  balance;
    private boolean isSKP;
    private String password;
    private boolean banned;

    public String getPassword() {
        return password;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public boolean isSKP() {
        return isSKP;
    }

    public void setSKP(boolean SKP) {
        isSKP = SKP;
    }

    @Override
    public String toString() {
        return "Student{" +
                "dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", degree='" + degree + '\'' +
                ", balance=" + balance +
                ", isSKP=" + isSKP +
                '}';
    }
}
