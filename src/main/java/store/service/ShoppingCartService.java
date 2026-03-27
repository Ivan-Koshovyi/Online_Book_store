package store.service;

import jakarta.validation.Valid;
import store.dto.BookInCartDto;
import store.dto.CartItemDto;
import store.dto.ShoppingCartDto;

public interface ShoppingCartService {

    ShoppingCartDto getCartByUserId(String username);

    boolean postItem(@Valid BookInCartDto book, String username);
}
