package com.project.stocktrading.controller.Basket;

import com.project.stocktrading.dao.Basket.BasketAbstractFactory;
import com.project.stocktrading.models.Basket.Basket;
import com.project.stocktrading.models.Basket.IBasket;
import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.service.Basket.IBasketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
@Controller
public class BasketController {

    private final IBasketService basketService;

    public BasketController(IBasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/baskets")
    public String getBaskets(Model model) {
        model.addAttribute("activepage", "baskets");
        model.addAttribute("basket", BasketAbstractFactory.getInstance().createNewBasket());

        ArrayList<IStock> stocks = this.basketService.getAllStocks();
        for (IStock stock : stocks) {
            stock.setPrice(this.basketService.getLatestStockPrice(stock.getId()).getPrice());
        }
        model.addAttribute("stocks", stocks);

        ArrayList<IBasket> baskets = this.basketService.getBaskets();
        for (IBasket basket : baskets) {
            basket.setChange(this.basketService.getBasketChange(basket.getId()));
            basket.setPrice(this.basketService.getLatestBasketPrice(basket.getId()));
        }
        model.addAttribute("baskets", baskets);

        return "baskets/index";
    }

    @PostMapping("/baskets")
    public String postBasket(@ModelAttribute Basket basket) {
        this.basketService.createBasketWithStocks(basket);
        return "redirect:/baskets/";
    }
}