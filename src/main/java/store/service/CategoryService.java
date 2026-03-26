package store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.dto.CategoryDto;

public interface CategoryService {
    Page<CategoryDto> getAllCategories(Pageable pageable);

    CategoryDto getCategoryById(long id);

    CategoryDto createNewCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategoryById(long id);
}
