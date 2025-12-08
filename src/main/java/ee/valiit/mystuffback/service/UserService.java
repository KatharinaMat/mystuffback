package ee.valiit.mystuffback.service;

import ee.valiit.mystuffback.controller.user.dto.UserDto;
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
    public static final int CUSTOMER_ROLE_ID = 2;
    public static final String CUSTOMER_ROLE_NAME = "customer";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Transactional
    public void addUser(@Valid UserDto userDto) {
        validateUserNameIsAvailable(userDto.getUsername());
        Role role = roleRepository.getRoleBy(CUSTOMER_ROLE_NAME);
        User user = userMapper.toUser(userDto);
        user.setRole(role);
        userRepository.save(user);
    }

    public User getValidUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("userId", userId));
    }

    private void validateUserNameIsAvailable(String username) {
        boolean usernameExists = userRepository.usernameExistsBy(username);
        if (usernameExists) {
            throw new ForbiddenException(USERNAME_UNAVAILABLE.getMessage(), USERNAME_UNAVAILABLE.getErrorCode());
        }
    }


}