package labs.pm.deliveryservice.client;

import labs.pm.deliveryservice.DTO.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/users/drivers/available")
    List<UserDto> getAvailableDrivers();
}
