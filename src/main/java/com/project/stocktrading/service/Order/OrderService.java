package com.project.stocktrading.service.Order;

import com.project.stocktrading.dao.Order.IOrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoling
 */

@Service
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;

    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public ArrayList<Map<String, Object>> getPendingOrders() {
        ArrayList<Map<String, Object>> orders = this.orderRepository.getPendingOrders();
        return orders;
    }

    public ArrayList<Map<String, Object>> getCompletedOrders() {
        ArrayList<Map<String, Object>> orders = this.orderRepository.getCompletedOrders();
        return orders;
    }

    public int deletePendingOrder(String pendingOrder) {
        Map<String, Object> deleteCondition = new HashMap<String, Object>();
        String newPendingOrder = pendingOrder.subSequence(1, pendingOrder.length() - 1).toString();
        for (String i : newPendingOrder.split(", ")) {
            deleteCondition.put(i.split("=")[0], i.split("=")[1]);
        }
        if (deleteCondition.get("type").equals("buy"))
            deleteCondition.put("type", 1);
        else
            deleteCondition.put("type", 0);
        return this.orderRepository.deletePendingOrder(deleteCondition);
    }

}
