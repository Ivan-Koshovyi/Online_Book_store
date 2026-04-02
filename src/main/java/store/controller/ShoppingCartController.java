package store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.dto.BookInCartDto;
import store.dto.CartItemDto;
import store.dto.ShoppingCartDto;
import store.dto.UpdateCartItemDto;
import store.service.ShoppingCartService;

@Tag(name = "Shopping cart", description = "Shopping cart for books")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Get shopping cart")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ShoppingCartDto getShoppingCart(@AuthenticationPrincipal UserDetails userDetails) {
        return shoppingCartService.getCartByUserId(userDetails.getUsername());
    }

    @Operation(summary = "Put item in shopping cart")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public CartItemDto postItemInCart(@RequestBody @Valid BookInCartDto book,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return shoppingCartService.postItem(book, userDetails.getUsername());
    }

    @Operation(summary = "Update item in shopping cart")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/items/{id}")
    public CartItemDto updateItemInCart(@PathVariable Long id,
                                        @RequestBody UpdateCartItemDto request) {
        return shoppingCartService.updateItem(id,request);
    }

    @Operation(summary = "Delete item in shopping cart")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/items/{id}")
    public void deleteItemInCart(@PathVariable Long id) {
        shoppingCartService.deleteItem(id);
    }
}
