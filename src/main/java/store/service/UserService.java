package store.service;

import store.dto.UserRequestDto;
import store.dto.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRequestDto user);
}
