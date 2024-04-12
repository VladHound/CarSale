package car.sale.userservice.controller.publicapi;

import car.sale.model.ErrorResponse;
import car.sale.userservice.dto.request.UserRegistrationDto;
import car.sale.userservice.dto.request.UserRequestDto;
import car.sale.userservice.dto.response.CreateUserResponse;
import car.sale.userservice.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "users")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid data provided to the server", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        }),
        @ApiResponse(responseCode = "401", description = "Authorization error"),
        @ApiResponse(responseCode = "403", description = "It is forbidden to receive a resource"),
        @ApiResponse(responseCode = "404", description = "The user with this ID was not found",
                content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                })
})
public interface UserApi {

    @Operation(
            summary = "User registration",
            responses = @ApiResponse(
                    responseCode = "201",
                    description = "Successful user creation",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreateUserResponse.class)
                            )
                    }
            )
    )
    CreateUserResponse create(
            @Parameter(name = "userRegistrationDto", description = "User registration")
            UserRegistrationDto userRegistrationDto
    );

    @Operation(
            summary = "Getting a user by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = @ApiResponse(responseCode = "200", description = "Successful user acquisition", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            }))
    @PreAuthorize(value = """
            @permissionService.hasPermission(
                #externalId,
                T(car.sale.auth.model.Role).USER,
                T(car.sale.auth.util.ClientAttributes).USER_ID
            )
            """
    )
    UserResponseDto getByExternalId(
            @Parameter(name = "id", description = "User ID", required = true) UUID externalId);


    @Operation(
            summary = "User update",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = @ApiResponse(responseCode = "200", description = "Successful user update", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            }))
    @PreAuthorize(value = """
            @permissionService.hasPermission(
                #externalId,
                T(car.sale.auth.model.Role).USER,
                T(car.sale.auth.util.ClientAttributes).USER_ID
            )
            """
    )
    UserResponseDto updateUser(
            @Parameter(name = "id", description = "user ID", required = true) UUID externalId,
            @Parameter(name = "UserRequestDto", description = "Update an existing user")
            UserRequestDto userRequestDto);

    @Operation(
            summary = "Delete user",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = @ApiResponse(responseCode = "204", description = "Successful deletion")
    )
    @PreAuthorize(value = """
            @permissionService.hasPermission(
                #externalId,
                T(car.sale.auth.model.Role).USER,
                T(car.sale.auth.util.ClientAttributes).USER_ID
            )
            """
    )
    void deleteUser(
            @Parameter(name = "id", description = "User ID", required = true) UUID externalId
    );


    @Operation(
            summary = "Make User Role Update",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = @ApiResponse(responseCode = "200", description = "Successful user update"))
    @PreAuthorize(value = """
        @permissionService.hasPermission(
            #userId,
            T(car.sale.auth.model.Role).USER,
            T(car.sale.auth.util.ClientAttributes).USER_ID
        )
        """
    )
    void makeUserSeller(
            @Parameter(name = "userId", description = "user ID", required = true) @PathVariable UUID userId);

}