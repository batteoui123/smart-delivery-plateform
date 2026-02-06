package labs.pm.deliveryservice.repo;

import labs.pm.deliveryservice.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepo extends JpaRepository<Delivery, Integer> {
}
