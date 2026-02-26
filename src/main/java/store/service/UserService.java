package store.service;

import store.dto.UserRegistrationRequestDto;
import store.dto.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto user);
}
