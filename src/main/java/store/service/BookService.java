package store.service;

import java.util.List;
import store.dto.BookDto;
import store.dto.CreateBookRequestDto;
import store.model.Book;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> getAll();
}
