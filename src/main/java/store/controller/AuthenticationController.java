package store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.dto.UserLoginRequestDto;
import store.dto.UserLoginResponseDto;
import store.dto.UserRegistrationRequestDto;
import store.dto.UserResponseDto;
import store.service.AuthenticationService;
import store.service.UserService;

@Tag(name = "Authentication",
        description = "API for registration and login user")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")

public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Registration a new user")
    @PostMapping("/registration")
    public UserResponseDto registerUser(@Valid @RequestBody UserRegistrationRequestDto user) {
        return userService.registerUser(user);
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
