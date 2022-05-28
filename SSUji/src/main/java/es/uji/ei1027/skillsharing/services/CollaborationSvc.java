package es.uji.ei1027.skillsharing.services;

import es.uji.ei1027.skillsharing.dao.*;
import es.uji.ei1027.skillsharing.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.desktop.OpenFilesEvent;
import java.util.*;

@Service
public class CollaborationSvc implements CollaborationService{

    @Autowired
    StudentDao studentDao;

    @Autowired
    SkillDao skillDao;

    @Autowired
    OfferDao offerDao;

    @Autowired
    RequestDao requestDao;

    @Autowired
    CollaborationDao collaborationDao;

    @Override
    public Map<Integer, Skill> getSkillsById() {
        List<Skill> skillList = skillDao.getSkills();
        Map<Integer, Skill> result = new HashMap<Integer, Skill>();
        for (Skill skill : skillList) {
            result.putIfAbsent(skill.getId(), skill);
        }
        return result;
    }

    public List<Collaboration> getCollaborationsByDni(String dni) {
        List<Collaboration> result = new ArrayList<>();
        List<Collaboration> collaborations = collaborationDao.getCollaborations();

        for (Collaboration c : collaborations) {
            Offer offer = offerDao.getOffer(c.getIdOffer());
            Request request = requestDao.getRequest(c.getIdRequest());

            if(offer.getDniOffer().equals(dni) || request.getDniRequest().equals(dni)){
                result.add(c);
            }
        }

//        System.out.print("getCollaborationsByDni " + collaborations);
        return result;
    }

    public boolean isBannedStudentInCollaboration(Collaboration c){
        Offer offer = offerDao.getOffer(c.getIdOffer());
        Request request = requestDao.getRequest(c.getIdRequest());

        Student studentOffer = studentDao.getStudent(offer.getDniOffer());
        Student studentRequest = studentDao.getStudent(request.getDniRequest());

        return studentOffer.isBanned() || studentRequest.isBanned();
    }

    public List<Collaboration> getCollaborationsByDniState(String dni, String state) {
        List<Collaboration> collaborations = getCollaborationsByDni(dni);
        List<Collaboration> collaborationsbn = new ArrayList<>();
        for (Collaboration coll : collaborations)
            if (coll.getState().equals(state) && !isBannedStudentInCollaboration(coll)) {
                collaborationsbn.add(coll);
            }
        return collaborationsbn;
    }

    @Override
    public String getAverageGivenScores(String dni){
        List<Collaboration> collaborations = getCollaborationsByDniState(dni, "finished");
        List<Integer> scores = new ArrayList<>();
        for (Collaboration c: collaborations) {
            if(requestDao.getRequest(c.getIdRequest()).getDniRequest().equals(dni)){
                if(c.getScore() > 0){
                    scores.add(c.getScore());
                }
            }
        }
        double result = scores.stream().mapToInt(val -> val).average().orElse(0.0);
        return String.format("%.1f", result);
    }

    @Override
    public String getAverageTeacherScores(String dni){
        List<Collaboration> collaborations = getCollaborationsByDniState(dni, "finished");
        List<Integer> scores = new ArrayList<>();
        for (Collaboration c: collaborations) {
            //c.getDniOffer().equals(dni)
            if(offerDao.getOffer(c.getIdOffer()).getDniOffer().equals(dni)){
                if(c.getScore() > 0){
                    scores.add(c.getScore());
                }

            }
        }
        double result = scores.stream().mapToInt(val -> val).average().orElse(0.0);
        return String.format("%.1f", result);
    }

    @Override
    public Map<Integer, Offer> getOffersById() {
        List<Offer> offerList = offerDao.getOffers();
        Map<Integer, Offer> result = new HashMap<>();
        for (Offer offer : offerList) {
            result.putIfAbsent(offer.getId(), offer);
        }
        return result;
    }

    @Override
    public Map<Integer, Request> getRequestsById() {
        List<Request> requestsList = requestDao.getRequests();
        Map<Integer, Request> result = new HashMap<>();
        for (Request request : requestsList) {
            result.putIfAbsent(request.getId(), request);
        }
        return result;
    }

    @Override
    public Map<String, Student> getStudentsByDni() {
        List<Student> requestsList = studentDao.getStudents();
        Map<String, Student> result = new HashMap<>();
        for (Student student : requestsList) {
            result.putIfAbsent(student.getDni(), student);
        }
        return result;
    }

    @Override
    public void updateStudentsBalance(Collaboration collaboration) {
        //mas balance para el estudiante de offer
        String dniOffer = offerDao.getOffer(collaboration.getIdOffer()).getDniOffer();
        Student studentOffer = studentDao.getStudent(dniOffer);
        studentDao.updateStudentBalance(studentOffer.getDni(), studentOffer.getBalance() + collaboration.getHours());

        //menos balance para el estudiante de request
        String dniRequest = requestDao.getRequest(collaboration.getIdRequest()).getDniRequest();
        Student studentRequest = studentDao.getStudent(dniRequest);
        studentDao.updateStudentBalance(studentRequest.getDni(), studentRequest.getBalance() - collaboration.getHours());
    }

    @Override
    public int getOffersNumber() {
        int num = offerDao.getOffers().size();
        return num;
    }

    @Override
    public int getRequestsNumber() {
        int num = requestDao.getRequests().size();
        return num;
    }

    @Override
    public int getCollaborationsNumber() {
        int num = collaborationDao.getCollaborations().size();
        return num;
    }

    @Override
    public int getSkillNumber() {
        int num = skillDao.getSkills().size();
        return num;
    }

    @Override
    public int getAverageCollaborations() {
        List<Collaboration> collaborations = collaborationDao.getCollaborationsByState("finished");
        int avr = 0;
        for(Collaboration collaboration : collaborations) {
            int num = collaboration.getScore();
            if(num > 0){
                avr = avr + num;
            }

        }

        if (collaborations.size() != 0){
            avr = avr / collaborations.size();
        }

        return avr;
    }

    @Override
    public Map<Integer, Float> getUsePercentageSkillsInOffers() {
        Map<Integer, Float> porcentajes = new HashMap<Integer, Float>();
        List<Offer> offers = offerDao.getOffersUnexpired();
        List<Skill> skills = skillDao.getSkillByActiveStatus(true);

        for (Skill skill : skills){
            int cnt = 0;
            for (Offer offer : offers){
                if (offer.getSkillId() == skill.getId()){
                    cnt ++;
                }
            }
            porcentajes.put(skill.getId(),(float) ((float)cnt / (float) offers.size()) * 100);
        }
        //System.out.println(porcentajes);
        return porcentajes;
    }

    @Override
    public Map<Integer, Float> getUsePercentageSkillsInRequests() {
        Map<Integer, Float> porcentajes = new HashMap<Integer, Float>();
        List<Request> requests = requestDao.getRequestsUnexpired();
        List<Skill> skills = skillDao.getSkillByActiveStatus(true);

        for (Skill skill : skills){
            int cnt = 0;
            for (Request request : requests){
                if (request.getSkillId() == skill.getId()){
                    cnt ++;
                }
            }

            porcentajes.put(skill.getId(),(float) ((float)cnt / (float) requests.size()) * 100);
        }
        //System.out.println(porcentajes);
        return porcentajes;
    }

    @Override
    public Map<Integer, Float> getUsePercentageSkillsInCollaborations() {
        Map<Integer, Float> porcentajes = new HashMap<Integer, Float>();
        List<Collaboration> collaborations = collaborationDao.getCollaborations();
        List<Skill> skills = skillDao.getSkillByActiveStatus(true);

        for (Skill skill : skills){
            int cnt = 0;
            for (Collaboration collaboration : collaborations){
                if (collaboration.getSkillId() == skill.getId()){
                    cnt ++;
                }
            }
//            System.out.println(cnt);
            porcentajes.put(skill.getId(),(float) ((float)cnt / (float) collaborations.size()) * 100);
        }
//        System.out.println( "Collaborations: "+ porcentajes);
        return porcentajes;
    }

    @Override
    public void finishAllOffersStudent(String dni) {
        List<Offer> offers = offerDao.getMyActiveOffers(dni);
        for (Offer offer : offers) {
            offerDao.deleteBySetFinishDate(offer.getId());
        }
    }

    @Override
    public void finishAllRequestStudent(String dni) {
        List<Request> requests = requestDao.getMyInactiveRequests(dni);
        for (Request request : requests) {
            requestDao.deleteBySetFinishDate(request.getId());
        }
    }

    @Override
    public void finishAllRequestSkill(int idSkill) {
        List<Request> requests = requestDao.getRequestsBySkill(idSkill);
        for (Request request : requests) {
            requestDao.deleteBySetFinishDate(request.getId());
        }
    }

    @Override
    public void finishAllOffersSkill(int idSkill) {
        List<Offer> offers = offerDao.getOffersBySkill(idSkill);
        for (Offer offer : offers) {
            offerDao.deleteBySetFinishDate(offer.getId());
        }
    }

    @Override
    public Map<Integer, Collaboration> getRequestCollaborationsStudent(String dni) {

        List<Collaboration> collaborations = getCollaborationsByDniState(dni, "finished");
        Map<Integer, Collaboration> result = new HashMap<>();
        for (Collaboration c : collaborations) {

            Request request = requestDao.getRequest(c.getIdRequest());
            String requestDni = studentDao.getStudent(request.getDniRequest()).getDni();

            if(dni.equals(requestDni)){
                result.put(c.getId(), c);
            }
        }
        return result;
    }

    @Override
    public List<Offer> getOffersForStudent(String dni){
        List<Offer> offers = offerDao.getOffersNotFromStudent(dni);
        List<Request> studentRequest = requestDao.getMyActiveRequests(dni);

        List<Integer> studentRequestsSkills = new ArrayList<>();
        for (Request r : studentRequest){
            studentRequestsSkills.add(r.getSkillId());
        }


        List<Offer> result = new ArrayList<>();
        for (Offer f : offers){
            if(studentRequestsSkills.contains(f.getSkillId())){
                result.add(f);
            }
        }
        return result;
    }
}
