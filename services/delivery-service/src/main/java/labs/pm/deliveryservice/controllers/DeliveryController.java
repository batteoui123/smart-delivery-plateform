package labs.pm.deliveryservice.controllers;

import labs.pm.deliveryservice.entities.Delivery;
import labs.pm.deliveryservice.service.servicesImp.DeliveryServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryServiceImp deliveryServiceImp;

    public DeliveryController(DeliveryServiceImp deliveryServiceImp) {
        this.deliveryServiceImp = deliveryServiceImp;
    }

    @PostMapping("/assign/{orderId}")
    public ResponseEntity<Delivery> assign(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(deliveryServiceImp.assignDriverToOrder(orderId));
    }
}
