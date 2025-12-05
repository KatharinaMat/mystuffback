package ee.valiit.mystuffback.persistence.user;

import ee.valiit.mystuffback.controller.login.dto.LoginResponse;
import ee.valiit.mystuffback.controller.user.dto.UserDto;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "role.name", target = "roleName")
    LoginResponse toLoginResponse(User user);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    UserDto toUserDto(User user);

    User toEntity(@Valid UserDto userDto);
}

