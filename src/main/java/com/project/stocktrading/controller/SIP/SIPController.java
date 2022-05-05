package com.project.stocktrading.controller.SIP;

import com.project.stocktrading.dao.SIP.SIPAbstractFactory;
import com.project.stocktrading.models.Basket.IBasket;
import com.project.stocktrading.models.SIP.ISIP;
import com.project.stocktrading.models.SIP.SIP;
import com.project.stocktrading.service.SIP.SIP.ISIPService;
import com.project.stocktrading.service.SIP.SIPBackground.ISIPBackgroundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
@Controller
public class SIPController {

    private final ISIPService sipService;
    private final ISIPBackgroundService sipBackgroundService;

    public SIPController(ISIPService sipService, ISIPBackgroundService sipBackgroundService) {
        this.sipService = sipService;
        this.sipBackgroundService = sipBackgroundService;
    }

    @GetMapping("/sip")
    public String getSIPs(Model model) {
        model.addAttribute("activePage", "sip");
        model.addAttribute("sip", SIPAbstractFactory.getInstance().createNewSIP());

        ArrayList<IBasket> baskets = this.sipService.getAllBaskets();

        for (IBasket basket : baskets) {
            basket.setPrice(this.sipService.getLatestBasketPrice(basket.getId()));
        }
        model.addAttribute("baskets", baskets);


        ArrayList<ISIP> sips = this.sipService.getSIPs();
        for (ISIP sip : sips) {
            sip.setChange(this.sipBackgroundService.getSIPChange(sip.getId()));
            sip.setPrice(this.sipService.getSIPPrice(sip.getId()));
        }
        model.addAttribute("sips", sips);

        return "sip/index";
    }

    @PostMapping("/sip")
    public String postSIP(@ModelAttribute SIP sip) {
        this.sipService.createSIPWithBaskets(sip);
        return "redirect:/sip/";
    }

    @GetMapping("/sip/{id}")
    public String getSIPAfterDeleting(@PathVariable(name = "id") int id) {
        this.sipService.deleteSIP(id);
        return "redirect:/sip/";
    }
}
