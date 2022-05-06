package es.uji.ei1027.skillsharing.services;

import es.uji.ei1027.skillsharing.model.Skill;

import java.util.List;
import java.util.Map;

public interface CollaborationService{
    public Map<Integer, Skill> getSkillsById();
    public double getAverageGivenScores(String dni);
    public double getAverageTeacherScores(String dni);
}
