package store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
