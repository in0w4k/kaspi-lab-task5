package kaspi.lab.task5.dto;

import lombok.Data;

@Data
public class DeliveryRequest {
    private Long productId;
    private String address;
}
