package ee.valiit.mystuffback.service;

import ee.valiit.mystuffback.controller.user.dto.UserDto;
import ee.valiit.mystuffback.infrastructure.exception.DataNotFoundException;
import ee.valiit.mystuffback.infrastructure.exception.ForbiddenException;
import ee.valiit.mystuffback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.valiit.mystuffback.persistence.role.Role;
import ee.valiit.mystuffback.persistence.role.RoleRepository;
import ee.valiit.mystuffback.persistence.user.User;
import ee.valiit.mystuffback.persistence.user.UserMapper;
import ee.valiit.mystuffback.persistence.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ee.valiit.mystuffback.infrastructure.error.Error.USERNAME_UNAVAILABLE;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Transactional
    public void addUser(@Valid UserDto userDto) {
        validateUserNameIsAvailable(userDto.getUsername());
        User user = userMapper.toEntity(userDto);
        Role role = roleRepository.findById(2).orElseThrow(() -> new DataNotFoundException("Default role not found", "Role doesn't exist"));
        user.setRole(role);
        user.setPassword(userDto.getPassword());
        user.setStatus("A");
        user.setStorage(100);
        userRepository.save(user);
    }

    public User getValidUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("username", username));
    }

    private void validateUserNameIsAvailable(String username) {
        boolean usernameExists = userRepository.usernameExistsBy(username);
        if (usernameExists) {
            throw new ForbiddenException(USERNAME_UNAVAILABLE.getMessage(), USERNAME_UNAVAILABLE.getErrorCode());
        }
    }

    public UserDto getUserDto(String username) {
        User user = getValidUser(username);
        UserDto userDto = userMapper.toUserDto(user);
        return userDto;
    }
}