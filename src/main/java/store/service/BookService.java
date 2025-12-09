package store.service;

import java.util.List;
import store.dto.BookDto;
import store.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> getAll();

    BookDto findById(Long id);
}
