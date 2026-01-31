package store.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import store.dto.BookDto;
import store.dto.BookSearchParametersDto;
import store.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> getAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    List<BookDto> searchBooks(BookSearchParametersDto params);
}
