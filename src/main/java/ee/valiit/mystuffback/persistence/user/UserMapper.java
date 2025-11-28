package ee.valiit.mystuffback.persistence.user;

import ee.valiit.mystuffback.controller.login.dto.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id",target = "userId")
    @Mapping(source="role.name", target="roleName")
    LoginResponse toLoginResponse(User user);
}
