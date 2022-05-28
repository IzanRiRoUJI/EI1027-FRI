package es.uji.ei1027.skillsharing.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.skillsharing.dao.OfferDao;
import es.uji.ei1027.skillsharing.dao.SkillDao;
import es.uji.ei1027.skillsharing.model.Offer;
import es.uji.ei1027.skillsharing.model.Student;
import es.uji.ei1027.skillsharing.services.CollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private OfferDao offerDao;

    @Autowired
    public void setOfferDao(OfferDao offerDao){
        this.offerDao=offerDao;
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
    public String listOffers(Model model) {
        model.addAttribute("offers", offerDao.getOffersUnexpired());
        model.addAttribute("skillsInfo", collaborationService.getSkillsById());
        model.addAttribute("studentsInfo", collaborationService.getStudentsByDni());

        return "offer/list";
    }

    @RequestMapping("/mylist")
    public String listMyOffers(Model model, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "/profile/myOffers");
        }


        Student user = (Student) session.getAttribute("user");
        if(user != null){
            String dni = user.getDni();
            model.addAttribute("ActiveOffers", offerDao.getMyActiveOffers(dni));
            model.addAttribute("InactiveOffers", offerDao.getMyInactiveOffers(dni));
        }


        model.addAttribute("skillsInfo", collaborationService.getSkillsById());
        model.addAttribute("goodMsg", session.getAttribute("goodMsg"));
        session.removeAttribute("goodMsg");

        return "profile/myOffers";
    }

    @RequestMapping(value="/add")
    public String addOffer(Model model, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "/offer/add");
        }
        model.addAttribute("offer", new Offer());
        model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
        session.removeAttribute("errorMsg");
        return "offer/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("offer") Offer offer, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()){
            return "offer/add";
        }

        if(offer.getStartDate().isAfter(offer.getEndDate())){
            session.setAttribute("errorMsg", "Error: the start date cannot be later than the end date :(");
            System.out.println("Error fecha primera posterior a ultima");
            model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
            return "offer/add";
        }

        if(offer.getEndDate().isBefore(LocalDate.now().plusDays(1))){
            session.setAttribute("errorMsg", "Error: the final day cannot be earlier than tomorrow :(");
            System.out.println("Error periodo ya paso");
            model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
            return "offer/add";
        }

        session.removeAttribute("errorMsg");
        offerDao.addOffer(offer);
        session.setAttribute("goodMsg", "The offer '" + offer.getName() + "' has been successfully added :)");
        return "redirect:mylist";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String updateOffer(Model model, @PathVariable int id, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "/offer/update");
        }
        Offer of = offerDao.getOffer(id);
        model.addAttribute("offer", of);
        model.addAttribute("skill", skillDao.getSkill(of.getSkillId()));
        session.removeAttribute("errorMsg");
        return "offer/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("offer") Offer offer, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors())
            return "offer/update";

        if(offer.getStartDate().isAfter(offer.getEndDate())){
            session.setAttribute("errorMsg", "Error: the start date cannot be later than the end date :(");
            System.out.println("Error fecha primera posterior a ultima");
            model.addAttribute("skill", skillDao.getSkill(offer.getSkillId()));
            return "offer/update";
        }

        if(offer.getEndDate().isBefore(LocalDate.now().plusDays(1))){
            session.setAttribute("errorMsg", "Error: the final day cannot be earlier than tomorrow :(");
            System.out.println("Error periodo ya paso");
            model.addAttribute("skill", skillDao.getSkill(offer.getSkillId()));
            return "offer/update";
        }


        session.removeAttribute("errorMsg");
        offerDao.updateOffer(offer);
        session.setAttribute("goodMsg", "The offer '" + offer.getName() + "' (" + offer.getId() + ") has been successfully updated :)");
        return "redirect:mylist";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "offer/list");
        }
        offerDao.deleteBySetFinishDate(id);
        return "redirect:/offer/mylist";
    }


}
