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

import java.util.Date;

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
        return "offer/list";
    }

    @RequestMapping("/mylist")
    public String listMyOffers(Model model, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "/profile/myOffers");
        }

        if(session != null){
            Student user = (Student) session.getAttribute("user");
            if(user != null){
                String dni = user.getDni();
                model.addAttribute("offers", offerDao.getMyOffers(dni));
            }
        }

        model.addAttribute("skillsInfo", collaborationService.getSkillsById());
        return "profile/myOffers";
    }

    @RequestMapping(value="/add")
    public String addOffer(Model model, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "/offer/add");
        }
        model.addAttribute("offer", new Offer());
        model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
        return "offer/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("offer") Offer offer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "offer/add";
        }
        offerDao.addOffer(offer);
        return "redirect:mylist";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String updateOffer(Model model, @PathVariable int id, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "/offer/update");
        }
        model.addAttribute("offer", offerDao.getOffer(id));
        model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
        return "offer/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("offer") Offer offer, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "offer/update";

        offerDao.updateOffer(offer);
        return "redirect:mylist";
    }

//    @RequestMapping(value="/delete/{dniOffer}/{skillId}")
//    public String processDelete(@PathVariable String dniOffer, @PathVariable int skillId, HttpSession session) {
//        if (session.getAttribute("nextUrl") == null){
//            session.setAttribute("nextUrl", "offer/list");
//        }
//        offerDao.deleteOffer(dniOffer, skillId);
//        return "redirect:/offer/list";
//    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "offer/list");
        }
        offerDao.deleteBySetFinishDate(id);
        // offerDao.deleteOffer(id);
        return "redirect:/offer/mylist";
    }


}
