package store.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.dto.CategoryDto;
import store.mapper.CategoryMapper;
import store.model.Category;
import store.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getCategoryById(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find category with id: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto createNewCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.save(categoryMapper.toEntity(categoryDto));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find category with id: " + id));
        categoryMapper.updateCategoryFromDto(categoryDto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategoryById(long id) {
        categoryRepository.deleteById(id);
    }
}
