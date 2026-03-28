package store.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import store.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserId(Long id);
}
