package store.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import store.service.ShoppingCartService;

@Tag(name = "Shopping cart", description = "Shopping cart for books")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    private ShoppingCartDto getShoppingCart(@AuthenticationPrincipal UserDetails userDetails) {
        return shoppingCartService.getCartByUserId(userDetails.getUsername());
    }

    @PostMapping
    private CartItemDto postItemInCart(@RequestBody @Valid BookInCartDto book,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return shoppingCartService.postItem(book, userDetails.getUsername());
    }

    @PutMapping("/items/{Id}")
    private CartItemDto updateItemInCart(@PathVariable Long id, int quantity) {
        return shoppingCartService.updateItem(id,quantity);
    }

    @DeleteMapping("/items/{Id}")
    private void deleteItemInCart(@PathVariable Long id) {
        shoppingCartService.deleteItem(id);
    }
}
