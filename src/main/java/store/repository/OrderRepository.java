package store.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import store.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserEmail(String email);
}
