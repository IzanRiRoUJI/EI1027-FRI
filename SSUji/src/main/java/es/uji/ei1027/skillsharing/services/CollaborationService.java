package es.uji.ei1027.skillsharing.services;

import es.uji.ei1027.skillsharing.model.Skill;
import java.util.List;
import java.util.Map;
import es.uji.ei1027.skillsharing.model.Collaboration;

public interface CollaborationService{
    public Map<Integer, Skill> getSkillsById();
    public double getAverageGivenScores(String dni);
    public double getAverageTeacherScores(String dni);
    public List<Collaboration> getCollaborationsByDni(String dni);
    public List<Collaboration> getCollaborationsByDniState(String dni,String notStarted);
}
