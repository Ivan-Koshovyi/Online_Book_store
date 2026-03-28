package store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import store.config.MapperConfig;
import store.dto.CategoryDto;
import store.dto.CreateCategoryRequest;
import store.model.Category;

@Mapper(config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    void updateCategoryFromDto(CreateCategoryRequest dto,
                           @MappingTarget Category category);

    Category toEntity(CreateCategoryRequest category);

}
