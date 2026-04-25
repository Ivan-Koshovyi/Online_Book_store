package store.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.dto.OrderItemResponseDto;
import store.dto.OrderRequestDto;
import store.dto.OrderResponseDto;
import store.dto.OrderUpdateDto;
import store.exception.EntityNotFoundException;
import store.exception.OrderProcessingException;
import store.mapper.OrderItemMapper;
import store.mapper.OrderMapper;
import store.model.Order;
import store.model.OrderItem;
import store.model.ShoppingCart;
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
        User user = getUser(username);
        ShoppingCart cart = getCart(user.getId());
        validateCart(cart, user.getId());

        Order order = createOrder(orderDto, user, cart);

        orderRepository.save(order);
        shoppingCartService.clearCart(user.getId());

        return orderMapper.toDto(order);
    }

    private Order createOrder(OrderRequestDto orderDto, User user, ShoppingCart cart) {
        Order order = orderMapper.toEntity(orderDto);

        Set<OrderItem> orderItems = mapToOrderItems(cart, order);
        BigDecimal totalPrice = calculateTotal(orderItems);
        order.setUser(user);
        order.setStatus(Order.Status.NEW);
        order.setTotal(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderItems(orderItems);

        return order;
    }

    private Set<OrderItem> mapToOrderItems(ShoppingCart cart, Order order) {
        return cart.getCartItems().stream()
                .map(item -> {
                    OrderItem orderItem = orderItemMapper.toOrderItem(item);
                    orderItem.setOrder(order);
                    orderItem.setBook(item.getBook());
                    return orderItem;
                })
                .collect(Collectors.toSet());
    }

    private BigDecimal calculateTotal(Set<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(
                        BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validateCart(ShoppingCart cart, Long userId) {
        if (cart.getCartItems().isEmpty()) {
            throw new OrderProcessingException(
                    "Cart is empty for userId = " + userId);
        }
    }

    private ShoppingCart getCart(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart not found for userId = " + userId)
                );
    }

    @Override
    public OrderResponseDto updateOrder(OrderUpdateDto orderDto, Long id) {
        Order order = getOrder(id);
        order.setStatus(orderDto.getStatus());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public Set<OrderItemResponseDto> getItems(Long id) {
        Order order = getOrder(id);
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderItemResponseDto getItemsForId(Long orderId, Long itemId) {
        Order order = getOrder(orderId);
        OrderItem item = order.getOrderItems().stream()
                .filter(oi -> oi.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        return orderItemMapper.toDto(item);
    }

    private User getUser(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }
}
