package store.service;

import store.dto.CartItemRequestDto;
import store.dto.ShoppingCartDto;
import store.dto.UpdateCartItemDto;
import store.model.User;

public interface ShoppingCartService {

    ShoppingCartDto getCartByUserId(String username);

    ShoppingCartDto postItem(CartItemRequestDto cartItemDto, String username);

    ShoppingCartDto updateItem(Long id, UpdateCartItemDto request, String username);

    void deleteItem(Long id, String username);

    void createShoppingCart(User user);
}
