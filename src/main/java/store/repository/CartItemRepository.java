package store.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import store.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndShoppingCartId(Long id, Long cartId);
}
