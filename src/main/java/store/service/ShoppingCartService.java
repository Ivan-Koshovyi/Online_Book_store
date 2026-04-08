package store.service;

import store.dto.CartItemDto;
import store.dto.CartItemRequestDto;
import store.dto.ShoppingCartDto;
import store.dto.UpdateCartItemDto;
import store.model.User;

public interface ShoppingCartService {

    ShoppingCartDto getCartByUserId(String username);

    CartItemDto postItem(CartItemRequestDto cartItemDto, String username);

    CartItemDto updateItem(Long id, UpdateCartItemDto request, String username);

    void deleteItem(Long id, String username);

    void createShoppingCart(User user);
}
