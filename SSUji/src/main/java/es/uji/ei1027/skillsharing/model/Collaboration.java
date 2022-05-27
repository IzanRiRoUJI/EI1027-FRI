package es.uji.ei1027.skillsharing.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public class Collaboration {
    private int id;
    private int idRequest;
    private int idOffer;
    private int skillId;
    private String place;
    private String state;
    private int score;
    private float hours;
//    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public int getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(int idOffer) {
        this.idOffer = idOffer;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Collaboration{" +
                "id=" + id +
                ", idRequest=" + idRequest +
                ", idOffer=" + idOffer +
                ", skillId=" + skillId +
                ", place='" + place + '\'' +
                ", state='" + state + '\'' +
                ", score=" + score +
                ", hours=" + hours +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
