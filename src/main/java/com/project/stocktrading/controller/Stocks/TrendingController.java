package com.project.stocktrading.controller.Stocks;

import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.service.Stocks.Trending.ITrendingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author abhishekuppe
 */
@Controller
public class TrendingController {

    private final ITrendingService trendingService;

    public TrendingController(ITrendingService trendingService) {
        this.trendingService = trendingService;
    }

    @GetMapping("/trending")
    public String getTrending(Model model) {
        List<IStock> stocks = this.trendingService.getTrendingStocks();

        model.addAttribute("activepage", "trending");
        model.addAttribute("stocks", stocks);

        return "trending/index";
    }
}
