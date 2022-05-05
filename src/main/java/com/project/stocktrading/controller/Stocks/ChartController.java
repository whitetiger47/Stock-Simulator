package com.project.stocktrading.controller.Stocks;

import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.service.Stocks.Chart.IChartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

/**
 * @author abhishekuppe
 */

@Controller
public class ChartController {

    private final IChartService chartService;

    public ChartController(IChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/chart/{id}")
    public String getChart(Model model, @PathVariable(name = "id") int id) {
        ArrayList<IStock> stocks = this.chartService.getStockPricesData(id);
        model.addAttribute("stocks", stocks);
        return "chart/index";
    }
}
