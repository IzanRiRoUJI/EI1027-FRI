package es.uji.ei1027.skillsharing.controller;

import es.uji.ei1027.skillsharing.dao.OfferDao;
import es.uji.ei1027.skillsharing.model.Offer;
import es.uji.ei1027.skillsharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private OfferDao offerDao;

    @Autowired
    public void setOfferDao(OfferDao offerDao){
        this.offerDao=offerDao;
    }

    @RequestMapping("/list")
    public String listOffers(Model model) {
        model.addAttribute("offers", offerDao.getOffers());
        return "offer/list";
    }

    @RequestMapping(value="/add")
    public String addOffer(Model model) {
        model.addAttribute("offer", new Offer());
        return "offer/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("offer") Offer offer,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "offer/add";
        offerDao.addOffer(offer);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{dniOffer}/{skillId}", method = RequestMethod.GET)
    public String updateOffer(Model model, @PathVariable String dniOffer, @PathVariable int skillId) {
        model.addAttribute("offer", offerDao.getOffer(dniOffer,skillId));
        return "offer/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("offer") Offer offer,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "offer/update";
        offerDao.updateOffer(offer);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dniOffer}/{skillId}")
    public String processDelete(@PathVariable String dniOffer, @PathVariable int skillId) {
        offerDao.deleteOffer(dniOffer, skillId);
        return "redirect:../list";
    }


}
