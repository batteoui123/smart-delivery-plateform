package labs.pm.orderservice.services;

import labs.pm.orderservice.DTO.OrderRequest;
import labs.pm.orderservice.DTO.OrderResponse;
import labs.pm.orderservice.entities.Order;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest order);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getAllOrdersOfUser(Long userId);
    void cancelOrder(Long orderId);

}
