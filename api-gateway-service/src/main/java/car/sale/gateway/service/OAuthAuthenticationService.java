package car.sale.gateway.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import car.sale.gateway.dto.LoginRequest;
import car.sale.gateway.dto.OAuthClient;
import car.sale.gateway.dto.OAuthClientResponse;
import car.sale.gateway.exception.AuthenticationException;

import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static car.sale.auth.util.ClientAttributes.USER_ID;
import static car.sale.exception.messages.ExceptionMessages.*;
import static car.sale.util.HttpUtils.PUBLIC_API_VI;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthAuthenticationService {
    private static final Map<String, OAuthClient> AUTHORIZED_CLIENTS_DATA = new ConcurrentHashMap<>(256);

    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    @Value(value = "${spring.security.oauth2.client.provider.custom-oidc.issuer-uri}")
    private String baseAuthUrl;

    public Mono<OAuthClientResponse> authenticateUser(Mono<LoginRequest> request) {
        return webClient.post()
                .uri(baseAuthUrl + "/" + PUBLIC_API_VI + "/users/oauth/token")
                .body(request, LoginRequest.class)
                .exchangeToMono(this::getAuthenticatedClientData)
                .doOnNext(data -> AUTHORIZED_CLIENTS_DATA.put(data.id(), data))
                .map(data ->
                        new OAuthClientResponse(data.id(), data.email(), data.accessToken())
                );
    }

    public Mono<OAuthClientResponse> authenticateSeller(Mono<LoginRequest> request) {
        return webClient.post()
                .uri(baseAuthUrl + "/" + PUBLIC_API_VI + "/sellers/oauth/token")
                .body(request, LoginRequest.class)
                .exchangeToMono(this::getAuthenticatedClientData)
                .doOnNext(data -> AUTHORIZED_CLIENTS_DATA.put(data.id(), data))
                .map(data -> new OAuthClientResponse(data.id(), data.email(), data.accessToken()));
    }

    public Mono<ResponseEntity<Void>> clearAuthentication(ServerHttpRequest request) {
        String accessToken = Optional.ofNullable(request.getHeaders().getFirst(AUTHORIZATION))
                .map(token -> token.replaceFirst(OAuth2AccessToken.TokenType.BEARER.getValue(), "").trim())
                .orElseThrow(
                        () -> new AuthenticationException(AUTHORIZATION_TOKEN_IS_NOT_PROVIDER)
                );

        String id = getClientId(accessToken);

        return Mono.defer(() -> {
            if (StringUtils.isNotBlank(accessToken) && isAuthenticated(accessToken, id))
                return Mono.fromSupplier(() -> sendRevocationRequest(request))
                        .flatMap(Function.identity())
                        .doOnNext(responseEntity -> AUTHORIZED_CLIENTS_DATA.remove(id))
                        .doOnNext(data -> log.info(AUTHORIZED_CLIENTS_DATA.toString()));
            else
                return Mono.error(() -> new AuthenticationException(CLIENT_NOT_AUTHENTICATED));
        });
    }

    public boolean isAuthenticated(String token) {
        return isAuthenticated(token, getClientId(token));
    }

    private boolean isAuthenticated(String token, String clientId) {
        if (StringUtils.isBlank(clientId)) {
            return false;
        }

        OAuthClient authenticatedUser = AUTHORIZED_CLIENTS_DATA.get(clientId);
        return authenticatedUser != null &&
                authenticatedUser.accessTokenExpiredAt().isAfter(Instant.now()) &&
                token.equals(authenticatedUser.accessToken());
    }

    private String getClientId(String token) {
        String[] tokenSegments = token.split("\\.");
        if (tokenSegments.length < 2)
            throw new AuthenticationException(CLIENT_NOT_AUTHENTICATED);

        Base64.Decoder decoder = Base64.getUrlDecoder();
        Map<String, Object> payload = convertToMap(new String(decoder.decode(tokenSegments[1])));
        return (String) payload.get(USER_ID);
    }

    private Mono<OAuthClient> getAuthenticatedClientData(ClientResponse clientResponse) {
        return clientResponse.statusCode().is2xxSuccessful()
                ? clientResponse.bodyToMono(OAuthClient.class)
                : Mono.error(() -> new AuthenticationException(CLIENT_NOT_AUTHENTICATED));
    }

    private Mono<ResponseEntity<Void>> sendRevocationRequest(ServerHttpRequest request) {
        return webClient.post()
                .uri(baseAuthUrl + PUBLIC_API_VI + "/oauth/revoke")
                .header(AUTHORIZATION, request.getHeaders().getFirst(AUTHORIZATION))
                .exchangeToMono(this::handleRevocationResponse);
    }

    private Mono<ResponseEntity<Void>> handleRevocationResponse(ClientResponse clientResponse) {
        return clientResponse.statusCode().is2xxSuccessful()
                ? clientResponse.toBodilessEntity()
                : Mono.error(() -> new AuthenticationException(CLIENT_NOT_AUTHENTICATED));
    }

    private Map<String, Object> convertToMap(String payload) {
        try {
            return objectMapper.readValue(payload, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Could not parse the payload - '{}'", payload);
            throw new AuthenticationException(INVALID_TOKEN_HAS_BEEN_PROVIDED);
        }
    }

}