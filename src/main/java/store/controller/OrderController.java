package store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.dto.OrderItemResponseDto;
import store.dto.OrderRequestDto;
import store.dto.OrderResponseDto;
import store.dto.OrderUpdateDto;
import store.service.OrderService;

@Tag(name = "Orders",
        description = "Operations related to managing "
                + "customer orders and order items")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Create a new order",
            description = "Creates a new order for the authenticated user based on cart items"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public OrderResponseDto addOrder(@RequestBody @Valid OrderRequestDto orderDto,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        return orderService.placeOrder(orderDto, userDetails.getUsername());
    }

    @Operation(
            summary = "Update order status",
            description = "Updates the status of an existing order (e.g., PROCESSING, COMPLETED)"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{id}")
    public OrderResponseDto updateOrder(@RequestBody @Valid OrderUpdateDto orderDto,
                                        @PathVariable Long id) {
        return orderService.updateOrder(orderDto, id);
    }

    @Operation(
            summary = "Get items for order",
            description = "Returns all items belonging to a specific order"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}/items")
    public Set<OrderItemResponseDto> getItemsForOrder(@PathVariable Long id) {
        return orderService.getItems(id);
    }

    @Operation(
            summary = "Get specific item from order",
            description = "Returns a specific item by itemId within a given order"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto getItemsForId(@PathVariable Long orderId,
                                              @PathVariable Long itemId) {
        return orderService.getItemsForId(orderId, itemId);
    }
}
