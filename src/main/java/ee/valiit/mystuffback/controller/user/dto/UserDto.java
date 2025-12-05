package ee.valiit.mystuffback.controller.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link ee.valiit.mystuffback.persistence.user.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    @NotNull
    @Size(message = "username must be 3-50 charactes", min = 3, max = 50)
    @NotEmpty
    private String username;
    @NotNull
    @Size(message = "password must be 3-50 characters", min = 3, max = 50)
    @NotEmpty
    private String password;
    @NotNull
    @Size(max = 50)
    @Email
    @NotEmpty
    private String email;
}