package es.uji.ei1027.skillsharing.controller;

import es.uji.ei1027.skillsharing.dao.*;
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

    private OfferDao offerDao;

    @Autowired
    public void setOfferDao(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    private RequestDao requestDao;

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    private CollaborationService collaborationService;

    @Autowired
    public void setCollaborationService(CollaborationService collaborationService) {
        this.collaborationService = collaborationService;
    }

    private SkillDao skillDao;

    @Autowired
    public void setSkillDao(SkillDao skillDao) {
        this.skillDao = skillDao;
    }

    private StudentDao studentDao;

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
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
        model.addAttribute("skillsInfo", collaborationService.getSkillsById());
        model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
        model.addAttribute("offers", offerDao.getOffersUnexpired());
        model.addAttribute("requests", requestDao.getRequestsUnexpired());
        model.addAttribute("studentsInfo", collaborationService.getStudentsByDni());
        return "collaboration/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("collaboration") Collaboration collaboration, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "collaboration/add";
        System.out.println("----------------------------COLLABORATION " + collaboration);
        collaborationDao.addCollaboration(collaboration);
        return "redirect:../collaboration/listmycollaborations";
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
        collaborationService.updateStudentsBalance(collaboration);
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
            session.setAttribute("nextUrl", "collaboration/listmycollaborations");
        }
        if (user == null){
           return "redirect:../login" ;
        }
        String dni = user.getDni();
        model.addAttribute("offersInfo", collaborationService.getOffersById());
        model.addAttribute("requestsInfo", collaborationService.getRequestsById());
        model.addAttribute("studentsInfo", collaborationService.getStudentsByDni());

        // System.out.println(model.getAttribute("offersInfo"));
        //System.out.println(model.getAttribute("studentsInfo"));
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
    public String processEditSubmit( @ModelAttribute("collaboration") Collaboration collaboration, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "collaboration/edit";
        collaborationDao.updateCollaboration(collaboration);
        collaborationService.updateStudentsBalance(collaboration);

        //actualizamos valores en session
        Student currentInfo = (Student) session.getAttribute("user");
        currentInfo.setBalance(studentDao.getStudent(currentInfo.getDni()).getBalance());
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
        return "redirect:../edit/"+ id;
    }

    @RequestMapping("/statistics")
    public String listStatistics(Model model, HttpSession session) {
        model.addAttribute("offersNumber", collaborationService.getOffersNumber());
        model.addAttribute("requestNumber", collaborationService.getRequestsNumber());
        model.addAttribute("collaborationsNumber", collaborationService.getCollaborationsNumber());
        model.addAttribute("skillNumber", collaborationService.getSkillNumber());
        model.addAttribute("collaborationAverage", collaborationService.getAverageCollaborations());
        model.addAttribute("collaborationPercentage", collaborationService.getUsePercentageSkillsInCollaborations());
        model.addAttribute("offerPercentage", collaborationService.getUsePercentageSkillsInOffers());
        model.addAttribute("requestPercentage", collaborationService.getUsePercentageSkillsInRequests());
        model.addAttribute("skillsInfo", collaborationService.getSkillsById());
        return "statistics";
    }
}
