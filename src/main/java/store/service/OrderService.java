package store.service;

import java.util.Set;
import store.dto.OrderItemResponseDto;
import store.dto.OrderRequestDto;
import store.dto.OrderResponseDto;
import store.dto.OrderUpdateDto;

public interface OrderService {

    OrderResponseDto placeOrder(OrderRequestDto orderDto, String username);

    OrderResponseDto updateOrder(OrderUpdateDto orderDto, Long id);

    Set<OrderItemResponseDto> getItems(Long id);

    OrderItemResponseDto getItemsForId(Long orderId, Long itemId);
}
