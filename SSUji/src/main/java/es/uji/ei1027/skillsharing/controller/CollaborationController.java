package es.uji.ei1027.skillsharing.controller;

import es.uji.ei1027.skillsharing.dao.*;
import es.uji.ei1027.skillsharing.model.*;
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
    public String addCollaboration(Model model, HttpSession session) {
        model.addAttribute("collaboration", new Collaboration());
        model.addAttribute("skillsInfo", collaborationService.getSkillsById());
        model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));


        Student currentStudent = (Student) session.getAttribute("user");
        model.addAttribute("requests", requestDao.getMyActiveRequests(currentStudent.getDni()));
        model.addAttribute("offers", collaborationService.getOffersForStudent(currentStudent.getDni()));

        model.addAttribute("studentsInfo", collaborationService.getStudentsByDni());
        session.removeAttribute("errorMsg");
        return "collaboration/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("collaboration") Collaboration collaboration, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors())
            return "collaboration/add";
//        System.out.println("----------------------------COLLABORATION " + collaboration);

        Offer offer = offerDao.getOffer(collaboration.getIdOffer());
        Request request = requestDao.getRequest(collaboration.getIdRequest());

        if(offer.getSkillId() != request.getSkillId()){
            System.out.println("Error: no se puede crear colaboración con habilidades distintas");
            session.setAttribute("errorMsg", "Error: the skill (and level) of the chosen offer and request must be the same :(");
            model.addAttribute("skillsInfo", collaborationService.getSkillsById());
            model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
            Student currentStudent = (Student) session.getAttribute("user");
            model.addAttribute("requests", requestDao.getMyActiveRequests(currentStudent.getDni()));
//            model.addAttribute("offers", offerDao.getOffersNotFromStudent(currentStudent.getDni()));
            model.addAttribute("offers", collaborationService.getOffersForStudent(currentStudent.getDni()));
            model.addAttribute("studentsInfo", collaborationService.getStudentsByDni());
            return "collaboration/add";
        }

        if(offer.getDniOffer().equals(request.getDniRequest())){
            System.out.println("Error: no se puede crear colaboración con el mismo estudiante");
            session.setAttribute("errorMsg", "Error: the offer and request must be from different students :(");
            model.addAttribute("skillsInfo", collaborationService.getSkillsById());
            model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
            Student currentStudent = (Student) session.getAttribute("user");
            model.addAttribute("requests", requestDao.getMyActiveRequests(currentStudent.getDni()));
            model.addAttribute("offers", offerDao.getOffersNotFromStudent(currentStudent.getDni()));
            model.addAttribute("studentsInfo", collaborationService.getStudentsByDni());
            return "collaboration/add";
        }

        collaboration.setSkillId(offer.getSkillId());
        collaborationDao.addCollaboration(collaboration);
        session.setAttribute("goodMsg", "The collaboration has been successfully created :)");
        session.removeAttribute("errorMsg");
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


        model.addAttribute("collaborationsNotStarted", collaborationService.getCollaborationsByDniState(dni,"notStarted"));
        model.addAttribute("collaborationsInProgress", collaborationService.getCollaborationsByDniState(dni,"inProgress"));
        model.addAttribute("collaborationsFinished", collaborationService.getCollaborationsByDniState(dni,"finished"));

        //Todo: solo el que recibe la ayuda puede poner la calificacion
        model.addAttribute("collaborationsIsRequest", collaborationService.getRequestCollaborationsStudent(dni));

        model.addAttribute("skillsInfo", collaborationService.getSkillsById());

        model.addAttribute("skillsInfo", collaborationService.getSkillsById());
        model.addAttribute("goodMsg", session.getAttribute("goodMsg"));
        session.removeAttribute("goodMsg");

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
    public String setCollaborationFinished(@PathVariable int id, HttpSession session) {


        Collaboration coll = collaborationDao.getCollaboration(id);
        collaborationDao.setCollaborationState(coll,"finished");

        //Todo: solo el que recibe la ayuda puede poner la calificacion
        Request request = requestDao.getRequest(coll.getIdRequest());
        String requestDni = studentDao.getStudent(request.getDniRequest()).getDni();
        Student studentSession = (Student) session.getAttribute("user");
        if (studentSession.getDni().equals(requestDni)){ //es el que hizo la oferta
            return "redirect:../edit/"+ id;
        }

        return "redirect:/collaboration/listmycollaborations";
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
