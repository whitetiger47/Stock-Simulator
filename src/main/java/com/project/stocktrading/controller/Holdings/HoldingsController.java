package com.project.stocktrading.controller.Holdings;

import com.project.stocktrading.service.Holdings.IHoldingsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @author zhaoling
 */

@Controller
public class HoldingsController {

    private final IHoldingsService holdingsService;

    public HoldingsController(IHoldingsService holdingsService) {
        this.holdingsService = holdingsService;
    }

    @GetMapping("/holdings")
    public String getHoldings(Model model) {
        List<Map<String, Object>> holdings = this.holdingsService.getAllHoldingsFromDb();

        model.addAttribute("holdings", holdings);

        return "holdings/index";
    }


}
