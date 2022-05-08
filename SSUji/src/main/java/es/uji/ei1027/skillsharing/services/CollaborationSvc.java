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
        List<Collaboration> collaborations = collaborationDao.getCollaborations() ;
        for(Collaboration coll : collaborations){
            if (Objects.equals(offerDao.getOffer(coll.getIdOffer()).getDniOffer(), dni))
                collaborations.add(coll);
            if (Objects.equals(requestDao.getRequest(coll.getIdRequest()).getDniRequest(), dni) && !collaborations.contains(coll))
                collaborations.add(coll);
        }
        return collaborations;
    }

    public List<Collaboration> getCollaborationsByDniState(String dni, String state) {
        List<Collaboration> collaborations = getCollaborationsByDni(dni);
        List<Collaboration> collaborationsaux = new ArrayList<>();
        for (Collaboration coll : collaborations) {
            if (Objects.equals(coll.getState(), state)) {
                collaborationsaux.add(coll);
            }
        }
        return collaborationsaux;
    }

    @Override
    public double getAverageGivenScores(String dni){
        List<Collaboration> collaborations = getCollaborationsByDniState(dni, "finished");
        List<Integer> scores = new ArrayList<>();
        for (Collaboration c: collaborations) {
            //c.getDniRequest().equals(dni))
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
}
