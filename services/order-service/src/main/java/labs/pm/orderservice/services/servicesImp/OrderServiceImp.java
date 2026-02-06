package labs.pm.orderservice.services.servicesImp;

import labs.pm.orderservice.DTO.OrderRequest;
import labs.pm.orderservice.DTO.OrderResponse;
import labs.pm.orderservice.DTO.UserDto;
import labs.pm.orderservice.DTO.mappers.OrderMapper;
import labs.pm.orderservice.OrderServiceApplication;
import labs.pm.orderservice.client.UserClient;
import labs.pm.orderservice.entities.Order;
import labs.pm.orderservice.repositories.OrderRepo;
import labs.pm.orderservice.services.OrderService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {
  private final OrderRepo orderRepo;
  private final UserClient userClient;
  private final OrderMapper orderMapper;
  private static final double price_per_km=12.00;
  private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderServiceImp(OrderRepo orderRepo, UserClient userClient, OrderMapper orderMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.orderRepo = orderRepo;
        this.userClient = userClient;
        this.orderMapper = orderMapper;
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public OrderResponse createOrder(OrderRequest request) {
        UserDto client=userClient.getUserById(request.getUserId());

        Order order = new Order();
        order.setUserId(client.getId());
        order.setDistance(request.getDistance());
        double price=price_per_km*order.getDistance();
        order.setPrice(price);
        Order savedOrder=orderRepo.save(order);
        String message = "{\"orderId\": " + order.getId() + ", \"userId\": " + order.getUserId() + "}";
        kafkaTemplate.send("order-topic", message);


        return orderMapper.toResponse(savedOrder,client);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        UserDto client=userClient.getUserById(orderRepo.findById(orderId).get().getUserId());
        return orderMapper.toResponse(orderRepo.findById(orderId).orElseThrow(()->new RuntimeException("the order is not found !")),client) ;
    }
    @Override
    public List<OrderResponse> getAllOrdersOfUser(Long userId) {
        return orderRepo.findAllByUserId(userId).stream()
                .map(order->orderMapper.toResponse(order,userClient.getUserById(userId)))
                .collect(Collectors.toList())
                ;
    }

    @Override
    public void cancelOrder(Long orderId) {

    }
}
