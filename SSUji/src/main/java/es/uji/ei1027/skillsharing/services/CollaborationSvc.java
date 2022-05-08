package es.uji.ei1027.skillsharing.services;

import es.uji.ei1027.skillsharing.dao.*;
import es.uji.ei1027.skillsharing.model.Collaboration;
import es.uji.ei1027.skillsharing.model.Offer;
import es.uji.ei1027.skillsharing.model.Request;
import es.uji.ei1027.skillsharing.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Collaboration> getCollaborationsByDniState(String dni, String state) {
        List<Collaboration> collaborations = getCollaborationsByDni(dni);
        List<Collaboration> collaborationsbn = new ArrayList<>();
        for (Collaboration coll : collaborations)
            if (coll.getState().equals(state)) {
                collaborationsbn.add(coll);
            }
        return collaborationsbn;
    }

    @Override
    public double getAverageGivenScores(String dni){
        List<Collaboration> collaborations = getCollaborationsByDniState(dni, "finished");
        List<Integer> scores = new ArrayList<>();
        for (Collaboration c: collaborations) {
            if(requestDao.getRequest(c.getIdRequest()).getDniRequest().equals(dni)){
                scores.add(c.getScore());
            }
        }
        return scores.stream().mapToInt(val -> val).average().orElse(0.0);
    }

    @Override
    public double getAverageTeacherScores(String dni){
        List<Collaboration> collaborations = getCollaborationsByDniState(dni, "finished");
        List<Integer> scores = new ArrayList<>();
        for (Collaboration c: collaborations) {
            //c.getDniOffer().equals(dni)
            if(offerDao.getOffer(c.getIdRequest()).getDniOffer().equals(dni)){
                scores.add(c.getScore());
            }
        }
        return scores.stream().mapToInt(val -> val).average().orElse(0.0);
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
}
