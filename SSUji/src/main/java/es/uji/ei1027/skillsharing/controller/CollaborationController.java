package es.uji.ei1027.skillsharing.controller;

import es.uji.ei1027.skillsharing.dao.CollaborationDao;
import es.uji.ei1027.skillsharing.model.Collaboration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class CollaborationController {
    private CollaborationDao collaborationDao;

    @Autowired
    public void setCollaborationDao(CollaborationDao collaborationDao){
        this.collaborationDao=collaborationDao;
    }

    @RequestMapping("/list")
    public String listCollaborations(Model model) {
        model.addAttribute("collaborations", collaborationDao.getCollaborations());
        return "collaboration/list";
    }

    @RequestMapping(value="/add")
    public String addCollaboration(Model model) {
        model.addAttribute("collaboration", new Collaboration());
        return "collaboration/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("collaboration") Collaboration collaboration,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "collaboration/add";
        collaborationDao.addCollaboration(collaboration);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{dniOffer}/{dniRequest}/{skillId}/{state}", method = RequestMethod.GET)
    public String updateCollaboration(Model model, @PathVariable String dniOffer, @PathVariable String dniRequest, @PathVariable int skillId, @PathVariable String state) {
        model.addAttribute("collaboration", collaborationDao.getCollaboration(dniOffer, dniRequest, skillId, state));
        return "collaboration/update";

//                dniOffer    VARCHAR(10),
//                dniRequest  VARCHAR(10),
//                skillId     SERIAL,
//                place       VARCHAR(100),
//                state       VARCHAR(50) DEFAULT 'notStarted',
//                score       INTEGER,
//                hours       FLOAT,
//                startDate   DATE,
//                endDate     DATE,
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("collaboration") Collaboration collaboration,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "collaboration/update";
        collaborationDao.updateCollaboration(collaboration);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dniOffer}/{dniRequest}/{skillId}/{state}")
    public String processDelete( @PathVariable String dniOffer, @PathVariable String dniRequest, @PathVariable int skillId, @PathVariable String state) {
        collaborationDao.deleteCollaboration(dniOffer, dniRequest, skillId, state);
        return "redirect:../list";
    }
}
