package labs.pm.orderservice.DTO;

import labs.pm.orderservice.entities.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Double distance;
    private double price;
    private OrderStatus status;
    private UserDto client;
}
