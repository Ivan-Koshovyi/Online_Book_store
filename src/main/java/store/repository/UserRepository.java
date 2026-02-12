package store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
