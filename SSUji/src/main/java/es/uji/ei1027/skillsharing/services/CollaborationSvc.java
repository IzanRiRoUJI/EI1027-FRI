package es.uji.ei1027.skillsharing.services;

import es.uji.ei1027.skillsharing.dao.*;
import es.uji.ei1027.skillsharing.model.Collaboration;
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
        Map<Integer, Skill> result =  new HashMap<Integer,Skill>();
        for (Skill skill: skillList) {
            result.putIfAbsent(skill.getId(), skill);
        }
        return result;
    }

    @Override
    public double getAverageGivenScores(String dni){
        List<Collaboration> collaborations = collaborationDao.getCollaborationsOfStudentByState(dni, "finished");
        List<Integer> scores = new ArrayList<>();
        for (Collaboration c: collaborations) {
            if(c.getDniRequest().equals(dni)){
                scores.add(c.getScore());
            }
        }

        return scores.stream().mapToInt(val -> val).average().orElse(0.0);
    }

    @Override
    public double getAverageTeacherScores(String dni){
        List<Collaboration> collaborations = collaborationDao.getCollaborationsOfStudentByState(dni, "finished");
        List<Integer> scores = new ArrayList<>();
        for (Collaboration c: collaborations) {
            if(c.getDniOffer().equals(dni)){
                scores.add(c.getScore());
            }
        }

        return scores.stream().mapToInt(val -> val).average().orElse(0.0);
    }
}
