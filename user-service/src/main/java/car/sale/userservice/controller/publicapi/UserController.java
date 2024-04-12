package car.sale.userservice.controller.publicapi;

import car.sale.auth.model.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.ResaleView;
import car.sale.userservice.dto.request.UserRegistrationDto;
import car.sale.userservice.dto.request.UserRequestDto;
import car.sale.userservice.dto.response.CreateUserResponse;
import car.sale.userservice.dto.response.UserResponseDto;
import car.sale.userservice.service.UserService;

import java.util.UUID;

import static car.sale.util.HttpUtils.PUBLIC_API_VI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PUBLIC_API_VI)
public class UserController implements UserApi {

    private final UserService userService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse create(@RequestBody UserRegistrationDto userRegistration) {
        return userService.signUp(userRegistration);
    }

    @GetMapping("/users/activation")
    @ResponseStatus(HttpStatus.OK)
    public void activeCode(@RequestParam("token") String token) {
        log.info("Received request to activation a user account");
        userService.confirmToken(token);
//        return new ResaleView(FRONTEND_URL + "/confirmation");
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getByExternalId(@PathVariable("userId") UUID userId) {
        log.info("Received request to get a user by id: {}", userId);
        return userService.findUserByExternalId(userId);
    }

    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateUser(@PathVariable("userId") UUID userId,
                                      @Valid @RequestBody(required = false) UserRequestDto userRequestDto) {
        log.info("Received request to update a user: {}", userRequestDto);
        return userService.update(userId, userRequestDto);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") UUID userId) {
        log.info("Received request to delete a user by id: {}", userId);
        userService.deleteUserByExternalId(userId);
    }

    // Make the User a Seller
    @PutMapping("/users/{userId}/roles/seller")
    @ResponseStatus(HttpStatus.OK)
    public void makeUserSeller(@PathVariable("userId") UUID userId) {
        userService.setToUserSellerRole(userId);
    }
}