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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping("/list")
    public String listOffers(Model model) {
        model.addAttribute("offers", offerDao.getOffers());
        return "offer/list";
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
        System.out.println("---------" + offer);

        if (bindingResult.hasErrors()){
            return "offer/add";
        }

        offerDao.addOffer(offer);
        return "redirect:list";
    }

//    @RequestMapping(value="/add/{name}/{skillName}/{skillLevel}/{start}/{end}", method= RequestMethod.POST)
//    public String processAddSubmit(@PathVariable String name, @PathVariable String skillName, @PathVariable String skillLevel, Date start, Date end) {
//        System.out.println("---------" + name + " - " + skillName + " - " + skillLevel + " - " + start + " - " + end);
//        return "redirect:list";
//    }


    @RequestMapping(value="/update/{dniOffer}/{skillId}", method = RequestMethod.GET)
    public String updateOffer(Model model, @PathVariable String dniOffer, @PathVariable int skillId, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "/offer/update");
        }
        model.addAttribute("offer", offerDao.getOffer(dniOffer,skillId));
        model.addAttribute("skillsActive", skillDao.getSkillByActiveStatus(true));
        return "offer/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("offer") Offer offer, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "offer/update";

        offerDao.updateOffer(offer);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dniOffer}/{skillId}")
    public String processDelete(@PathVariable String dniOffer, @PathVariable int skillId, HttpSession session) {
        if (session.getAttribute("nextUrl") == null){
            session.setAttribute("nextUrl", "offer/list");
        }
        offerDao.deleteOffer(dniOffer, skillId);
        return "redirect:/offer/list";
    }


}
