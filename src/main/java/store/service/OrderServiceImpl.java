package store.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.dto.OrderRequestDto;
import store.dto.OrderResponseDto;
import store.exception.EntityNotFoundException;
import store.mapper.OrderItemMapper;
import store.mapper.OrderMapper;
import store.model.CartItem;
import store.model.Order;
import store.model.OrderItem;
import store.model.User;
import store.repository.OrderRepository;
import store.repository.ShoppingCartRepository;
import store.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartService shoppingCartService;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderDto, String username) {
        Order order = orderMapper.toEntity(orderDto);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Set<CartItem> items = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found"))
                .getCartItems();
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Shopping cart is empty");
        }
        Set<OrderItem> orderItems = new HashSet<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem item : items) {
            OrderItem orderItem = orderItemMapper.toOrderItem(item);
            orderItem.setOrder(order);
            orderItem.setBook(item.getBook());
            totalPrice = totalPrice.add(
                    orderItem.getPrice().multiply(
                            BigDecimal.valueOf(orderItem.getQuantity())
                    )
            );
            orderItems.add(orderItem);
        }
        order.setUser(user);
        order.setStatus(Order.Status.NEW);
        order.setTotal(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderItems(orderItems);
        for (OrderItem orderItem : orderItems) {
            System.out.println("OrderItem id = " + orderItem.getId());
        }
        orderRepository.save(order);
        shoppingCartService.clearCart(user.getId());
        return orderMapper.toDto(order);
    }
}
