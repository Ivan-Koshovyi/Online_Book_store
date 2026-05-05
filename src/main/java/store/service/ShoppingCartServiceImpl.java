package store.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.dto.CartItemRequestDto;
import store.dto.ShoppingCartDto;
import store.dto.UpdateCartItemDto;
import store.exception.EntityNotFoundException;
import store.mapper.ShoppingCartMapper;
import store.model.Book;
import store.model.CartItem;
import store.model.ShoppingCart;
import store.model.User;
import store.repository.BookRepository;
import store.repository.CartItemRepository;
import store.repository.ShoppingCartRepository;
import store.repository.UserRepository;

@Service
@Transactional
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
        ShoppingCart shoppingCart = getShoppingCartByUser(user.getId());
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto postItem(CartItemRequestDto cartItemRequestDto, String username) {

        User user = getUser(username);

        ShoppingCart cart = getShoppingCartByUser(user.getId());

        Book book = bookRepository.findById(cartItemRequestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + cartItemRequestDto.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setBook(book);
            newItem.setQuantity(cartItemRequestDto.getQuantity());
            newItem.setShoppingCart(cart);

            cart.getCartItems().add(newItem);
        }

        shoppingCartRepository.save(cart);

        return shoppingCartMapper.toDto(cart);
    }

    @Override
    public ShoppingCartDto updateItem(Long id, UpdateCartItemDto request, String username) {
        User user = getUser(username);

        ShoppingCart cart = getShoppingCartByUser(user.getId());

        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(id, cart.getId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toDto(cart);
    }

    @Override
    public void deleteItem(Long id, String username) {
        User user = getUser(username);

        ShoppingCart cart = getShoppingCartByUser(user.getId());

        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(id, cart.getId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        cartItemRepository.delete(cartItem);
    }

    private User getUser(String user) {
        return userRepository.findByEmail(user)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private ShoppingCart getShoppingCartByUser(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found"));
    }

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void clearCart(Long userId) {
        ShoppingCart cart = getShoppingCartByUser(userId);
        cart.clearItems();
    }
}
