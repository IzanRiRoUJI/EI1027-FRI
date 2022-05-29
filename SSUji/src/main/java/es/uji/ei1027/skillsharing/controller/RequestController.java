package es.uji.ei1027.skillsharing.controller;

import es.uji.ei1027.skillsharing.dao.RequestDao;
import es.uji.ei1027.skillsharing.dao.SkillDao;
import es.uji.ei1027.skillsharing.model.Request;
import es.uji.ei1027.skillsharing.model.Student;
import es.uji.ei1027.skillsharing.services.CollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestDao requestDao;

    @Autowired
    public void setRequestDao(RequestDao requestDao){
        this.requestDao=requestDao;
    }

    private SkillDao skillDao;

    @Autowired
    public void setSkillDao(SkillDao skillDao) {
        this.skillDao = skillDao;
    }

    private CollaborationService collaborationService;

    @Autowired
    public void setCollaborationService(CollaborationService collaborationService) {
        this.collaborationService = collaborationService;
    }

    @RequestMapping("/list")
    public String listRequests(Model model, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "request/list");
        }
        model.addAttribute("requests", requestDao.getRequestsUnexpired());
        model.addAttribute("skillsInfo", collaborationService.getSkillsById());
        model.addAttribute("studentsInfo", collaborationService.getStudentsByDni());
        return "request/list";
    }
    @RequestMapping("/mylist")
    public String listMyOffers(Model model, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "request/mylist");
        }
        Student user = (Student) session.getAttribute("user");
        if(user != null){
            String dni = user.getDni();
            model.addAttribute("ActiveRequests", requestDao.getMyActiveRequests(dni));
            model.addAttribute("InactiveRequests", requestDao.getMyInactiveRequests(dni));
        }

        model.addAttribute("skillsInfo", collaborationService.getSkillsById());
        model.addAttribute("goodMsg", session.getAttribute("goodMsg"));
        session.removeAttribute("goodMsg");

        return "profile/myRequests";
    }

    @RequestMapping(value="/add")
    public String addRequest(Model model, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "/offer/add");
        }
        model.addAttribute("request", new Request());
        model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
        session.removeAttribute("errorMsg");
        return "request/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("request") Request request, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors())
            return "request/add";


        if(request.getStartDate().isAfter(request.getEndDate())){
            session.setAttribute("errorMsg", "Error: the start date cannot be later than the end date :(");
            model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
            return "request/add";
        }

        if(request.getEndDate().isBefore(LocalDate.now().plusDays(1))){
            session.setAttribute("errorMsg", "Error: the final day cannot be earlier than tomorrow :(");
            model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
            return "request/add";
        }

        session.removeAttribute("errorMsg");
        requestDao.addRequest(request);
        session.setAttribute("goodMsg", "The offer '" + request.getName() + "' has been successfully added :)");
        return "redirect:mylist";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String updateRequest(Model model, @PathVariable int id, HttpSession session) {

        Request r = requestDao.getRequest(id);
        model.addAttribute("request", r);
        model.addAttribute("skill", skillDao.getSkill(r.getSkillId()));
        session.removeAttribute("errorMsg");
        return "request/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("request") Request request, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors())
            return "request/update";

        if(request.getStartDate().isAfter(request.getEndDate())){
            session.setAttribute("errorMsg", "Error: the start date cannot be later than the final :(");
            model.addAttribute("skill", skillDao.getSkill(request.getSkillId()));
            return "request/update";
        }

        if(request.getEndDate().isBefore(LocalDate.now().plusDays(1))){
            session.setAttribute("errorMsg", "Error: the final day cannot be earlier than tomorrow :(");
            model.addAttribute("skill", skillDao.getSkill(request.getSkillId()));
            return "request/update";
        }

        session.removeAttribute("errorMsg");
        requestDao.updateRequest(request);
        session.setAttribute("goodMsg", "The offer '" + request.getName() + "' (" + request.getId() + ") has been successfully updated :)");
        return "redirect:mylist";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable int id) {
        //requestDao.deleteRequest(id);
        requestDao.deleteBySetFinishDate(id);
        return "redirect:../mylist";
    }




}
