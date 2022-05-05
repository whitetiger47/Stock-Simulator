package com.project.stocktrading.service.Order;

import com.project.stocktrading.dao.Order.IOrderRepository;
import com.project.stocktrading.dao.Order.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author zhaoling
 */
public class OrderServiceTest {

    private static final ArrayList<Map<String, Object>> pendingOrders = new ArrayList<Map<String, Object>>();
    private static final ArrayList<Map<String, Object>> completedOrders = new ArrayList<Map<String, Object>>();
    private static IOrderService orderService;

    @BeforeAll
    public static void init() {
        IOrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);

        Map<String, Object> pendingOrder = new HashMap<String, Object>();
        pendingOrder.put("stock_id", 1);
        pendingOrder.put("name", "Shopify");
        pendingOrder.put("price", new BigDecimal("11"));
        pendingOrder.put("quantity", 10);
        pendingOrder.put("type", "buy");
        pendingOrder.put("mode", "regular");
        pendingOrder.put("status", "limit");
        pendingOrders.add(pendingOrder);

        Map<String, Object> completedOrder = new HashMap<String, Object>();
        completedOrder.put("stock_id", 1);
        completedOrder.put("name", "Shopify");
        completedOrder.put("price", new BigDecimal("11"));
        completedOrder.put("quantity", 10);
        completedOrder.put("type", "buy");
        completedOrder.put("mode", "regular");
        completedOrder.put("status", "market");
        completedOrders.add(completedOrder);

        Map<String, Object> deleteCondition = new HashMap<String, Object>();
        deleteCondition.put("stock_id", "1");
        deleteCondition.put("price", "11");
        deleteCondition.put("type", 1);
        deleteCondition.put("mode", "regular");
        deleteCondition.put("quantity", "10");

        Mockito.when(orderRepository.getPendingOrders()).thenReturn(pendingOrders);
        Mockito.when(orderRepository.getCompletedOrders()).thenReturn(completedOrders);
        Mockito.when(orderRepository.deletePendingOrder(deleteCondition)).thenReturn(1);

    }

    @Test
    public void testGetPendingOrders() {
        assertEquals(pendingOrders, orderService.getPendingOrders());
    }

    @Test
    public void testGetCompletedOrders() {
        assertEquals(completedOrders, orderService.getCompletedOrders());
    }

    @Test
    public void testDeletePendingOrder() {
        String pendingOrder = "stock_id=1, price=11, type=buy, mode=regular, quantity=10";
        int expectResult = 0;
        assertEquals(expectResult, orderService.deletePendingOrder(pendingOrder));
    }
}
