package store.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import store.model.OrderItem;

@Getter
@Setter
public class OrderResponseDto {
    private String orderId;
    private String userId;
    private OrderItem orderItems;
    private LocalDateTime orderDate;
    private Long total;
    private String status;

}
