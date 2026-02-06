package labs.pm.orderservice.controllers;

import labs.pm.orderservice.DTO.OrderRequest;
import labs.pm.orderservice.DTO.OrderResponse;
import labs.pm.orderservice.repositories.OrderRepo;
import labs.pm.orderservice.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService, OrderRepo orderRepo) {
        this.orderService = orderService;
    }

    @PostMapping("/save")
    public ResponseEntity<OrderResponse> saveOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest)) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id)) ;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderResponse>> getAllOrders(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getAllOrdersOfUser(id));
    }
}
