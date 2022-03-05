package es.uji.ei1027.skillsharing.model;

import java.time.LocalTime;

public class Collaboration {
    private String dniOffer;
    private String dniRequest;
    private int skillId;
    private String place;
    private String state;
    private int score;
    private float hours;
    private LocalTime startDate;
    private LocalTime endDate;

    public String getDniOffer() {
        return dniOffer;
    }

    public void setDniOffer(String dniOffer) {
        this.dniOffer = dniOffer;
    }

    public String getDniRequest() {
        return dniRequest;
    }

    public void setDniRequest(String dniRequest) {
        this.dniRequest = dniRequest;
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

    public LocalTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalTime startDate) {
        this.startDate = startDate;
    }

    public LocalTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Collaboration{" +
                "dniOffer='" + dniOffer + '\'' +
                ", dniRequest='" + dniRequest + '\'' +
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
