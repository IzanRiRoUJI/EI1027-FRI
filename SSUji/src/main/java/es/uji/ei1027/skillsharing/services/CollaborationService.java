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
    public int getOffersNumber();
    public int getRequestsNumber();
    public int getCollaborationsNumber();
    public int getSkillNumber();
    public int getAverageCollaborations();
    public Map<Integer, Float> getUsePercentageSkillsInOffers();
    public Map<Integer, Float> getUsePercentageSkillsInRequests();
    public Map<Integer, Float> getUsePercentageSkillsInCollaborations();

    public void finishAllRequestStudent(String dni);
    public void finishAllOffersStudent(String dni);
//    public void finishAllCollaborationsStudent(String dni);
}
