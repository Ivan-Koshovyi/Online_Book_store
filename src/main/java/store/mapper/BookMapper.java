package store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import store.config.MapperConfig;
import store.dto.BookDto;
import store.dto.CreateBookRequestDto;
import store.model.Book;

@Mapper(config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {
    BookDto toDto(Book book);

    void updateBookFromDto(CreateBookRequestDto dto,
                           @MappingTarget Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
