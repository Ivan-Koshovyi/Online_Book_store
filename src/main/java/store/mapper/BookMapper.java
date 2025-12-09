package store.mapper;

import org.mapstruct.Mapper;
import store.config.MapperConfig;
import store.dto.BookDto;
import store.dto.CreateBookRequestDto;
import store.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
