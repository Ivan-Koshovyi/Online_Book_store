package store.repository;

import store.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> getAll();
}
