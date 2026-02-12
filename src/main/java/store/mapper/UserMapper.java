package store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import store.config.MapperConfig;
import store.dto.UserRequestDto;
import store.dto.UserResponseDto;
import store.model.User;

@Mapper(config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto userResponseDto);
}
