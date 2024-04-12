package car.sale.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import car.sale.gateway.exception.AuthenticationException;
import car.sale.gateway.exception.MissingAuthHeaderException;
import car.sale.gateway.service.OAuthAuthenticationService;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;

@Slf4j
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private final Map<PathPattern, List<String>> openedRoutes;
    private final OAuthAuthenticationService authenticationService;

    public AuthFilter(Map<PathPattern, List<String>> openedRoutes, OAuthAuthenticationService authenticationService) {
        super(Config.class);
        this.authenticationService = authenticationService;
        this.openedRoutes = openedRoutes;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            PathContainer requestedPath = exchange.getRequest().getPath().pathWithinApplication();
            log.info("""
                            Http request is passing auth filter:
                            Path - '{}'.
                            Method - '{}'.
                            Headers - '{}'.
                            Cookies - '{}'.
                     """,
                    requestedPath.value(),
                    exchange.getRequest().getMethod(),
                    exchange.getRequest().getHeaders(),
                    exchange.getRequest().getCookies());

            if (openedRoutes.entrySet().stream().noneMatch(route -> isOpenedRoute(requestedPath, exchange, route))) {
                String bearerToken = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);

                if (StringUtils.hasText(bearerToken)) {
                    String token = bearerToken.replaceFirst(BEARER.getValue(), "").trim();

                    if (!authenticationService.isAuthenticated(token)) {
                        throw new AuthenticationException("Client is not authenticated");
                    }

                } else {
                    throw new MissingAuthHeaderException("Authorization header must be provided");
                }
            }
            return chain.filter(exchange);
        };
    }

    private boolean isOpenedRoute(PathContainer requestedPath,
                                  ServerWebExchange exchange,
                                  Map.Entry<PathPattern, List<String>> route) {
        return route.getKey().matches(requestedPath)
                && route.getValue().contains(exchange.getRequest().getMethod().name());
    }

    public static class Config {}
}

//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            PathContainer requestedPath = exchange.getRequest().getPath().pathWithinApplication();
//            log.info("""
//                                   Http request is passing auth filter:
//                                   Path - '{}'.
//                                   Method - '{}'.
//                                   Headers - '{}'.
//                                   Cookies - '{}'.
//                            """,
//                    requestedPath.value(),
//                    exchange.getRequest().getMethod(),
//                    exchange.getRequest().getHeaders(),
//                    exchange.getRequest().getCookies());
//
//            if (openedRoutes.entrySet().stream().noneMatch(route -> isOpenedRoute(requestedPath, exchange, route))) {
//                String bearerToken = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
//
//                if (StringUtils.hasText(bearerToken)) {
//                    if (!authenticationService.isAuthenticated(
//                            bearerToken.replaceFirst(BEARER.getValue(), "").trim())
//                    ) {
//                        throw new AuthenticationException("Client is not authenticated");
//                    }
//
//                } else {
//                    throw new MissingAuthHeaderException("Authorization header must be provided");
//                }
//            }
//            return chain.filter(exchange);
//        };
//    }