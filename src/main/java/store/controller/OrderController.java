package store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.dto.OrderRequestDto;
import store.dto.OrderResponseDto;
import store.service.OrderService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public OrderResponseDto addOrder(@RequestBody @Valid OrderRequestDto orderDto,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        return orderService.placeOrder(orderDto, userDetails.getUsername());
    }
}
