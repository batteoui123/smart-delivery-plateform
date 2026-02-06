package labs.pm.deliveryservice.service.servicesImp;

import labs.pm.deliveryservice.DTO.UserDto;
import labs.pm.deliveryservice.DeliveryServiceApplication;
import labs.pm.deliveryservice.client.UserClient;
import labs.pm.deliveryservice.entities.Delivery;
import labs.pm.deliveryservice.entities.DeliveryStatus;
import labs.pm.deliveryservice.repo.DeliveryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImp implements DeliveryService {
    private final UserClient userClient;
    private final DeliveryRepo deliveryRepo;

    public DeliveryServiceImp(UserClient userClient, DeliveryRepo deliveryRepo) {
        this.userClient = userClient;
        this.deliveryRepo = deliveryRepo;
    }

    @Override
    public Delivery assignDriverToOrder(Long orderId) {
        List<UserDto> drivers=userClient.getAvailableDrivers();
        if(drivers.isEmpty()) throw new RuntimeException("aucun driver n'est disponible");
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setDriverId(drivers.get(0).getId());
        delivery.setStatus(DeliveryStatus.ASSIGNED);
        return deliveryRepo.save(delivery);
    }
}
