package es.uji.ei1027.skillsharing.services;

import es.uji.ei1027.skillsharing.model.*;

import java.util.List;
import java.util.Map;

public interface CollaborationService{
    public Map<Integer, Skill> getSkillsById();
    public String getAverageGivenScores(String dni);
    public String getAverageTeacherScores(String dni);
    public List<Collaboration> getCollaborationsByDni(String dni);
    public List<Collaboration> getCollaborationsByDniState(String dni,String notStarted);
    public Map<Integer, Offer> getOffersById();
    public Map<Integer, Request> getRequestsById();
    public Map<String, Student> getStudentsByDni();
    public void updateStudentsBalance(Collaboration collaboration);
}
