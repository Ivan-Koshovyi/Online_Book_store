package store.service;

import store.dto.OrderRequestDto;
import store.dto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto placeOrder(OrderRequestDto orderDto, String username);
}
