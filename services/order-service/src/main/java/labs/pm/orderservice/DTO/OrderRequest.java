package labs.pm.orderservice.DTO;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private Double distance;
}
