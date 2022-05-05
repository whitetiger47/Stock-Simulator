package com.project.stocktrading.controller.Orders;

import com.project.stocktrading.service.Order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author zhaoling
 */

@Controller
public class OrdersController {

    private final IOrderService orderService;

    @Autowired
    public OrdersController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String getOrdersHome() {
        return "redirect:/orders/index";
    }

    @GetMapping("/orders/{pendingOrder}")
    public String getOrders(Model model, @PathVariable(name = "pendingOrder") String pendingOrder) {
        ArrayList<Map<String, Object>> pendingOrders = this.orderService.getPendingOrders();
        ArrayList<Map<String, Object>> completedOrders = this.orderService.getCompletedOrders();
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("completedOrders", completedOrders);
        return "orders/index";
    }

    @PostMapping("/orders/{pendingOrder}")
    public String postOrders(Model model, @PathVariable(name = "pendingOrder") String pendingOrder) {
        this.orderService.deletePendingOrder(pendingOrder);
        return "redirect:/orders/index";
    }
}
