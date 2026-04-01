package store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.dto.CategoryDto;
import store.dto.CreateCategoryRequest;
import store.exception.EntityNotFoundException;
import store.mapper.CategoryMapper;
import store.model.Category;
import store.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto getCategoryById(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find category with id: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto createNewCategory(CreateCategoryRequest newCategory) {
        Category category = categoryRepository.save(categoryMapper.toEntity(newCategory));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, CreateCategoryRequest category) {
        Category newCategory = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find category with id: " + id));
        categoryMapper.updateCategoryFromDto(category, newCategory);
        return categoryMapper.toDto(categoryRepository.save(newCategory));
    }

    @Override
    public void deleteCategoryById(long id) {
        categoryRepository.deleteById(id);
    }
}
