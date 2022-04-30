package es.uji.ei1027.skillsharing.services;

import es.uji.ei1027.skillsharing.dao.*;
import es.uji.ei1027.skillsharing.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
