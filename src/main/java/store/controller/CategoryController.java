package store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.dto.BookDto;
import store.dto.CategoryDto;
import store.service.BookService;
import store.service.CategoryService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Category management", description = "Endpoints for managing category")
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @Operation(summary = "Create category")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.createNewCategory(categoryDto);
    }

    @Operation(summary = "Get all category")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Get category for id")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public CategoryDto findByCategoryId(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Update category for id")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(id, categoryDto);
    }

    @Operation(summary = "Delete category for id")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }

    @Operation(summary = "Get all books for category id")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}/books")
    public List<BookDto> findBooksByCategoryId(@PathVariable Long id, Pageable pageable) {
        return bookService.getBooksByCategory(id, pageable).getContent();
    }
}
