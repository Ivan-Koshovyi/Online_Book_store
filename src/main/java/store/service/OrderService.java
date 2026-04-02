package store.service;

import jakarta.validation.Valid;
import store.dto.OrderRequestDto;

public interface OrderService {

    void placeOrder(@Valid OrderRequestDto orderDto);
}
