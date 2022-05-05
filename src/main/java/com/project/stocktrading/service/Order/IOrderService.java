package com.project.stocktrading.service.Order;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author zhaoling
 */
public interface IOrderService {

    ArrayList<Map<String, Object>> getPendingOrders();

    ArrayList<Map<String, Object>> getCompletedOrders();

    int deletePendingOrder(String pendingOrder);
}
