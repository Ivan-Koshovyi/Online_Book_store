package store.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.dto.BookDto;
import store.dto.CreateBookRequestDto;
import store.service.BookService;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> findAll() {
        return bookService.getAll();
    }

    @PostMapping
    public BookDto createBook(@RequestBody CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @GetMapping("/{id}")
    public BookDto findBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
