package labs.pm.deliveryservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import labs.pm.deliveryservice.service.servicesImp.DeliveryService;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderEventListener {
    private final DeliveryService deliveryService;
    private final ObjectMapper objectMapper;

    public OrderEventListener(DeliveryService deliveryService, ObjectMapper objectMapper) {
        this.deliveryService = deliveryService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order-topic",groupId = "delivery-group")
    public void listen(String event) {
        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(event, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        map.entrySet().forEach(System.out::println);
        Long orderId = ((Number) map.get("orderId")).longValue();
        System.out.println("the order id is " + orderId);
        deliveryService.assignDriverToOrder(orderId);

    }



}
