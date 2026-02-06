package labs.pm.orderservice.DTO.mappers;

import labs.pm.orderservice.DTO.OrderResponse;
import labs.pm.orderservice.DTO.UserDto;
import labs.pm.orderservice.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order, UserDto userDto) {
        if(order==null) return null;
        return OrderResponse.builder()
                .id(order.getId())
                .price(order.getPrice())
                .distance(order.getDistance())
                .status(order.getStatus())
                .client(userDto)
                .build();

    }
}
