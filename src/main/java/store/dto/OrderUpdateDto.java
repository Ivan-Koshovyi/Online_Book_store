package store.dto;

import lombok.Getter;
import lombok.Setter;
import store.model.Order;

@Getter
@Setter
public class OrderUpdateDto {
    private Order.Status status;
}
