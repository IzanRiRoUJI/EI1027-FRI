package es.uji.ei1027.skillsharing.model;

import java.time.LocalTime;

public class Request {
    private String name;
    private String dniRequest;
    private int skillId;
    private String description;
    private LocalTime startDate;
    private LocalTime endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "Request{" +
                "name='" + name + '\'' +
                ", dniRequest='" + dniRequest + '\'' +
                ", skillId=" + skillId +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
