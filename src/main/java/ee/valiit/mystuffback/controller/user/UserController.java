package ee.valiit.mystuffback.controller.user;

import ee.valiit.mystuffback.controller.user.dto.UserDto;
import ee.valiit.mystuffback.infrastructure.error.ApiError;
import ee.valiit.mystuffback.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    @Operation(summary = "New user account creation", description = "all fields are mandatory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Account with this name already exists",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public void addUser(@RequestBody @Valid UserDto userDto) {
        userService.addUser(userDto);
    }
    @GetMapping
    @Operation(summary = "Get user by username", description = "Returns user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public UserDto getUser(@RequestParam String username) {
        return userService.getUserDto(username);
    }


}