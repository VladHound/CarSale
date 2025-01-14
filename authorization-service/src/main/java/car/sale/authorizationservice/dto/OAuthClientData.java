package car.sale.authorizationservice.dto;

import java.time.Instant;

public record OAuthClientData(
        String id,
        String email,
        String accessToken,
        String refreshToken,
        Instant accessTokenExpiredAt
) {}