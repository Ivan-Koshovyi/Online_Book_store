package store.service;

import java.util.List;
import store.dto.CategoryDto;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(long id);

    CategoryDto createNewCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategoryById(long id);
}
