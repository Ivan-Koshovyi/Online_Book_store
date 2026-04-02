package store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.model.OrderItem;


public interface OrderRepository extends JpaRepository<OrderItem, Long> {
}
