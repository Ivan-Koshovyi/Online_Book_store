package store.service;

import jakarta.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.dto.UserRequestDto;
import store.dto.UserResponseDto;
import store.exception.RegistrationExseption;
import store.mapper.UserMapper;
import store.model.Role;
import store.model.User;
import store.repository.RoleRepository;
import store.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RegistrationExseption("User already exists");
        }
        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        if (userRequestDto.getRoles() != null) {
            Set<Role> roles = userRequestDto.getRoles().stream()
                    .map(role -> roleRepository.findByRole(role)
                            .orElseThrow(()
                                    -> new IllegalArgumentException("Role not found: " + role)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }
        return userMapper.toDto(userRepository.save(user));
    }
}
