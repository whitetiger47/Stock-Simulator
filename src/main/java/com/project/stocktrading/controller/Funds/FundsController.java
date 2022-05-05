package com.project.stocktrading.controller.Funds;
/**
 * @author Smit_Thakkar
 */


import com.project.stocktrading.models.History.History;
import com.project.stocktrading.models.History.IHistory;
import com.project.stocktrading.models.User.User;
import com.project.stocktrading.service.Funds.FundsService;
import com.project.stocktrading.service.History.IHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;


@Controller
public class FundsController {

    private final FundsService fundsService;
    private final IHistoryService historyService;

    public FundsController(FundsService fundsService, IHistoryService historyService) {
        this.fundsService = fundsService;
        this.historyService = historyService;
    }

    @GetMapping("/funds")
    public String getFunds(Model model) {
        model.addAttribute("activepage", "funds");
        BigDecimal userFunds = this.fundsService.getUserFunds();
        IHistory history = new History();
        history.setPrice(userFunds);
        history.setValue("deposit");
        this.historyService.addToFundHistory(history);
        model.addAttribute("userFunds", userFunds);
        User user = new User();
        model.addAttribute("user", user);
        return "funds/index";
    }

    @PostMapping("/funds")
    public String withdrawFunds(@ModelAttribute("user") User user) {
        this.fundsService.withdrawUserFunds(user);
        IHistory history = new History();
        history.setPrice(user.getFunds());
        history.setValue("withdraw");
        this.historyService.addToFundHistory(history);
        return ("redirect:/funds");

    }

    @PostMapping("/depositfunds")
    public String depositFunds(@ModelAttribute("user") User user) {
        this.fundsService.depositUserFunds(user);
        return ("redirect:/funds");

    }

}
