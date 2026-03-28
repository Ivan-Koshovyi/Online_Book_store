package store.service;

import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import store.dto.BookInCartDto;
import store.dto.CartItemDto;
import store.dto.ShoppingCartDto;
import store.exception.BookNotFoundException;
import store.exception.CartItemNotFoundExseption;
import store.mapper.ShoppingCartMapper;
import store.model.CartItem;
import store.model.ShoppingCart;
import store.model.User;
import store.repository.BookRepository;
import store.repository.CartItemRepository;
import store.repository.ShoppingCartRepository;
import store.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto getCartByUserId(String username) {
        User user = getUser(username);
        ShoppingCart shoppingCart = getShoppingCartByUser(user);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public CartItemDto postItem(BookInCartDto book, String username) {
        User user = getUser(username);
        CartItem cartItem = new CartItem();
        ShoppingCart shoppingCart = getShoppingCartByUser(user);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(bookRepository.findById(book.getBookId()).orElseThrow(
                () -> new BookNotFoundException("Book not found")));
        cartItem.setQuantity(book.getQuantity());
        return shoppingCartMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemDto updateItem(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new CartItemNotFoundExseption("Item not found"));
        cartItem.setQuantity(quantity);
        return shoppingCartMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new CartItemNotFoundExseption("Item not found"));
        cartItemRepository.delete(cartItem);
    }

    private User getUser(String user) {
        return userRepository.findByEmail(user)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private ShoppingCart getShoppingCartByUser(User user) {
        return shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    newCart.setCartItems(new HashSet<>());
                    return shoppingCartRepository.save(newCart);
                });
    }
}
