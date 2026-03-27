package store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
