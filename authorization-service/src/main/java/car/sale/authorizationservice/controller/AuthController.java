package car.sale.authorizationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.web.bind.annotation.*;
import car.sale.authorizationservice.dto.OAuthClientData;
import car.sale.authorizationservice.dto.UserLoginRequest;
import car.sale.authorizationservice.service.AuthenticationService;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static car.sale.util.HttpUtils.PUBLIC_API_VI;

@RestController
@RequiredArgsConstructor
@RequestMapping(PUBLIC_API_VI)
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/users/oauth/token")
    @ResponseStatus(HttpStatus.OK)
    public OAuthClientData login(@RequestBody UserLoginRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/oauth/revoke")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokeAuth(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst(AUTHORIZATION);
        authenticationService.revokeAuthentication(token, OAuth2TokenType.ACCESS_TOKEN);
        headers.remove(AUTHORIZATION);
    }

    @PostMapping("/sellers/oauth/token")
    @ResponseStatus(HttpStatus.OK)
    public OAuthClientData loginSeller(@RequestBody UserLoginRequest request) {
        return authenticationService.authenticateSeller(request);
    }
}