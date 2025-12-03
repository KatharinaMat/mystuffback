package ee.valiit.mystuffback.service;

import ee.valiit.mystuffback.persistence.user.UserMapper;
import ee.valiit.mystuffback.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
}
