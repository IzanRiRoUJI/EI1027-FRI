package es.uji.ei1027.skillsharing.model;

import java.time.LocalTime;

public class Offer {
    private String name;
    private String dniOffer;
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

    public String getDniOffer() {
        return dniOffer;
    }

    public void setDniOffer(String dniOffer) {
        this.dniOffer = dniOffer;
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
        return "Offer{" +
                "name='" + name + '\'' +
                ", dniOffer='" + dniOffer + '\'' +
                ", skillId=" + skillId +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
