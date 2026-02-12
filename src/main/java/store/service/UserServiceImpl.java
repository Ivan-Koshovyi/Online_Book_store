package store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.dto.UserRequestDto;
import store.dto.UserResponseDto;
import store.mapper.UserMapper;
import store.model.User;
import store.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
