package es.uji.ei1027.skillsharing.services;

import es.uji.ei1027.skillsharing.model.*;

import java.util.List;
import java.util.Map;

public interface CollaborationService{
    Map<Integer, Skill> getSkillsById();
    String getAverageGivenScores(String dni);
    String getAverageTeacherScores(String dni);
    List<Collaboration> getCollaborationsByDni(String dni);
    List<Collaboration> getCollaborationsByDniState(String dni,String notStarted);
    Map<Integer, Offer> getOffersById();
    Map<Integer, Request> getRequestsById();
    Map<String, Student> getStudentsByDni();
    void updateStudentsBalance(Collaboration collaboration);
    int getOffersNumber();
    int getRequestsNumber();
    int getCollaborationsNumber();
    int getSkillNumber();
    int getAverageCollaborations();
    Map<Integer, Float> getUsePercentageSkillsInOffers();
    Map<Integer, Float> getUsePercentageSkillsInRequests();
    Map<Integer, Float> getUsePercentageSkillsInCollaborations();

    void finishAllRequestStudent(String dni);
    void finishAllOffersStudent(String dni);

    void finishAllOffersSkill(int idSkill);
    void finishAllRequestSkill(int idSkill);

}
