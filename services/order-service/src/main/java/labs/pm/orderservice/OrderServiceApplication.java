package labs.pm.orderservice;

import labs.pm.orderservice.DTO.UserDto;
import labs.pm.orderservice.client.UserClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

     @Bean
    CommandLineRunner run(UserClient userClient) {
        return args -> {

//            UserDto user=userClient.getUserById(Long.valueOf(2));
//            System.out.println(user.getId()+","+user.getFirstName()+","+user.getLastName());
        };
    }

}
