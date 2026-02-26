package store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.dto.UserLoginRequestDto;
import store.dto.UserLoginResponseDto;
import store.dto.UserRegistrationRequestDto;
import store.dto.UserResponseDto;
import store.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserResponseDto registerUser(@Valid @RequestBody UserRegistrationRequestDto user) {
        return userService.registerUser(user);
    }

    @GetMapping("/login")
    public UserLoginResponseDto login(UserLoginRequestDto request) {
        return null;
    }
}
