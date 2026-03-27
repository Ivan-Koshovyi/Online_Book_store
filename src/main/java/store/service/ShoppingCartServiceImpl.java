package store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import store.dto.BookInCartDto;
import store.dto.CartItemDto;
import store.dto.ShoppingCartDto;
import store.exception.BookNotFoundException;
import store.mapper.ShoppingCartMapper;
import store.model.CartItem;
import store.model.ShoppingCart;
import store.model.User;
import store.repository.BookRepository;
import store.repository.CartItemRepository;
import store.repository.ShoppingCartRepository;
import store.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;

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
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    ShoppingCart newCart  = new ShoppingCart();
                    newCart.setUser(user);
                    newCart.setCartItems(new HashSet<>());
                    return shoppingCartRepository.save(newCart );
                });
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public boolean postItem(BookInCartDto book, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCartRepository.findByUserId(user.getId()).orElseGet(() -> {
            ShoppingCart newCart  = new ShoppingCart();
            newCart.setUser(user);
            newCart.setCartItems(new HashSet<>());
            return shoppingCartRepository.save(newCart);
        }));
        cartItem.setBook(bookRepository.findById(book.getBookId()).orElseThrow(
                () -> new BookNotFoundException("Book not found")));
        cartItem.setQuantity(book.getQuantity());
        cartItemRepository.save(cartItem);
        return true;
    }
}
