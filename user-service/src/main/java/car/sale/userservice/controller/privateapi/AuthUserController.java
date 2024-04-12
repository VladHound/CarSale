package car.sale.userservice.controller.privateapi;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import car.sale.auth.dto.ClientAuthDetails;
import car.sale.userservice.service.UserService;

import static car.sale.util.HttpUtils.PRIVATE_API_VI;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping(PRIVATE_API_VI)
public class AuthUserController {

    private final UserService userService;

    @GetMapping("/users/auth")
    public ClientAuthDetails authenticate(@RequestParam String email) {
        return userService.getUserDetails(email);
    }
}