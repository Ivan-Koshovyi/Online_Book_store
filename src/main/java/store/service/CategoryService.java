package store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.dto.CategoryDto;
import store.dto.CreateCategoryRequest;

public interface CategoryService {
    Page<CategoryDto> getAllCategories(Pageable pageable);

    CategoryDto getCategoryById(long id);

    CategoryDto createNewCategory(CreateCategoryRequest newCategory);

    CategoryDto updateCategory(Long id, CreateCategoryRequest category);

    void deleteCategoryById(long id);
}
