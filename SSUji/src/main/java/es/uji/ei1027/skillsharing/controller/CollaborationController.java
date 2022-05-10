package es.uji.ei1027.skillsharing.controller;

import es.uji.ei1027.skillsharing.dao.CollaborationDao;
import es.uji.ei1027.skillsharing.dao.SkillDao;
import es.uji.ei1027.skillsharing.model.Collaboration;
import es.uji.ei1027.skillsharing.model.Skill;
import es.uji.ei1027.skillsharing.model.Student;
import es.uji.ei1027.skillsharing.services.CollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/collaboration")
public class CollaborationController {
    private CollaborationDao collaborationDao;

    @Autowired
    public void setCollaborationDao(CollaborationDao collaborationDao){
        this.collaborationDao=collaborationDao;
    }

    private CollaborationService collaborationService;

    @Autowired
    public void setCollaborationService(CollaborationService collaborationService) {
        this.collaborationService = collaborationService;
    }


    @RequestMapping("/list")
    public String listCollaborations(Model model, HttpSession session) {
        model.addAttribute("collaborationsNotStarted", collaborationDao.getCollaborationsByState("notStarted"));
        model.addAttribute("collaborationsInProgress", collaborationDao.getCollaborationsByState("inProgress"));
        model.addAttribute("collaborationsFinished", collaborationDao.getCollaborationsByState("finished"));
        model.addAttribute("offersInfo", collaborationService.getOffersById());
        model.addAttribute("requestsInfo", collaborationService.getRequestsById());
        model.addAttribute("skillsInfo", collaborationService.getSkillsById());
        model.addAttribute("studentsInfo", collaborationService.getStudentsByDni());
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

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String updateCollaboration(Model model, @PathVariable int id) {
        model.addAttribute("collaboration", collaborationDao.getCollaboration(id));
        return "collaboration/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("collaboration") Collaboration collaboration, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "collaboration/update";
        collaborationDao.updateCollaboration(collaboration);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete( @PathVariable int id) {
        collaborationDao.deleteCollaboration(id);
        return "redirect:../list";
    }

    @RequestMapping("/listmycollaborations")
    public String listMyCollaborations(Model model, HttpSession session) {
        Student user = (Student) session.getAttribute("user");
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "profile/listmycollaborations");
        }
        if (user == null){
           return "redirect:../login" ;
        }
        String dni = user.getDni();
        model.addAttribute("offersInfo", collaborationService.getOffersById());
        model.addAttribute("requestsInfo", collaborationService.getRequestsById());
        model.addAttribute("studentsInfo", collaborationService.getStudentsByDni());

        System.out.println(model.getAttribute("offersInfo"));
        System.out.println(model.getAttribute("studentsInfo"));
        model.addAttribute("collaborationsNotStarted", collaborationService.getCollaborationsByDniState(dni,"notStarted"));
        model.addAttribute("collaborationsInProgress", collaborationService.getCollaborationsByDniState(dni,"inProgress"));
        model.addAttribute("collaborationsFinished", collaborationService.getCollaborationsByDniState(dni,"finished"));
        model.addAttribute("skillsInfo", collaborationService.getSkillsById());
        return "profile/mycollaborations";
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public String editCollaboration(Model model, @PathVariable int id) {
        model.addAttribute("collaboration", collaborationDao.getCollaboration(id));
        return "collaboration/edit";
    }

    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public String processEditSubmit( @ModelAttribute("collaboration") Collaboration collaboration, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "collaboration/edit";
        collaborationDao.updateCollaboration(collaboration);
        return "redirect:/collaboration/listmycollaborations";
    }
    @RequestMapping(value="/setInProgress/{id}")
    public String setCollaborationInProgress(@PathVariable int id) {
        collaborationDao.setCollaborationState(collaborationDao.getCollaboration(id),"inProgress");
        return "redirect:../listmycollaborations";
    }
    @RequestMapping(value="/setFinished/{id}")
    public String setCollaborationFinished(@PathVariable int id) {
        collaborationDao.setCollaborationState(collaborationDao.getCollaboration(id),"finished");
        return "redirect:../listmycollaborations";
    }
}
