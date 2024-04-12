package car.sale.gateway.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import car.sale.gateway.dto.LoginRequest;
import car.sale.gateway.dto.OAuthClientResponse;

@Tag(name = "Сервис авторизации")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Неавторизован")
})
public interface SignApi {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно авторизовался"),
    })
    Mono<OAuthClientResponse> loginUser(@RequestBody Mono<LoginRequest> request);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продавец успешно авторизовался"),
    })
    Mono<OAuthClientResponse> loginSeller(@RequestBody Mono<LoginRequest> request);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешно вышел из системы")
    })
    @Parameters(value = {
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", required = true)
    })
    Mono<ResponseEntity<Void>> logout(ServerHttpRequest request);
}
