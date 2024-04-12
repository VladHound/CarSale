package car.sale.gateway.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import car.sale.gateway.dto.LoginRequest;
import car.sale.gateway.dto.OAuthClientResponse;
import car.sale.gateway.service.OAuthAuthenticationService;

import static car.sale.util.HttpUtils.PUBLIC_API_VI;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(PUBLIC_API_VI)
public class SignController implements SignApi {

    @Autowired
    private final OAuthAuthenticationService authenticationService;

    @PostMapping("/users/oauth/login")
    public Mono<OAuthClientResponse> loginUser(@RequestBody Mono<LoginRequest> request) {
        return authenticationService.authenticateUser(request);
    }

    @PostMapping("/sellers/oauth/login")
    public Mono<OAuthClientResponse> loginSeller(@RequestBody Mono<LoginRequest> request) {
        return authenticationService.authenticateSeller(request);
    }

    @PostMapping("/oauth/logout")
    public Mono<ResponseEntity<Void>> logout(ServerHttpRequest request) {
        return authenticationService.clearAuthentication(request);
    }
}