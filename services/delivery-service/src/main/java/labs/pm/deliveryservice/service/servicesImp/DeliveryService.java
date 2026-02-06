package labs.pm.deliveryservice.service.servicesImp;

import labs.pm.deliveryservice.entities.Delivery;

public interface DeliveryService {
      Delivery assignDriverToOrder(Long orderId);
}
