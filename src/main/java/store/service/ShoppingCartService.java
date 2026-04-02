package store.service;

import jakarta.validation.Valid;
import store.dto.BookInCartDto;
import store.dto.CartItemDto;
import store.dto.ShoppingCartDto;
import store.dto.UpdateCartItemDto;

public interface ShoppingCartService {

    ShoppingCartDto getCartByUserId(String username);

    CartItemDto postItem(@Valid BookInCartDto book, String username);

    CartItemDto updateItem(Long id, UpdateCartItemDto request);

    void deleteItem(Long id);
}
