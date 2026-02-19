package store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.dto.UserRequestDto;
import store.dto.UserResponseDto;
import store.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping(value = "/registration")
    public UserResponseDto registerUser(@Valid @RequestBody UserRequestDto user) {
        return userService.registerUser(user);
    }
}
