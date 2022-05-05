package com.project.stocktrading.dao.Order;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author zhaoling
 */
public interface IOrderRepository {

    ArrayList<Map<String, Object>> getPendingOrders();

    ArrayList<Map<String, Object>> getCompletedOrders();

    int deletePendingOrder(Map<String, Object> buySell);

}
