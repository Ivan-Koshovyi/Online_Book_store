package store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}
