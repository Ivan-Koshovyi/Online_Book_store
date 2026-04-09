package store.service;

import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.dto.UserRegistrationRequestDto;
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
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto) {
        if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
            throw new RegistrationExseption(
                    "User already exists: "
                            + userRegistrationRequestDto.getFirstName()
                            + " (" + userRegistrationRequestDto.getEmail() + ")"
            );
        }
        User user = userMapper.toEntity(userRegistrationRequestDto);
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
        Role userRole = roleRepository.findByRoleName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: ROLE_USER"));
        user.setRoles(Set.of(userRole));
        User savedUser = userRepository.save(user);
        shoppingCartService.createShoppingCart(savedUser);

        return userMapper.toDto(savedUser);
    }
}
