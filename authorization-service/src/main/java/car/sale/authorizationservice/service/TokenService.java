package car.sale.authorizationservice.service;

import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

import static car.sale.auth.util.ClientAttributes.SELLER_ID;
import static car.sale.auth.util.ClientAttributes.USER_ID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;

    @Value("${services.auth.url}")
    private String AUTH_SERVICE_URL;

    private JwtClaimsSet createJwtClaimsSet(Authentication authentication, long expiresAtInMinutes) {
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Instant issuedAt = Instant.now(),
                expiredAt = issuedAt.plus(expiresAtInMinutes, ChronoUnit.MINUTES);

        return JwtClaimsSet.builder()
                .issuer(AUTH_SERVICE_URL)
                .issuedAt(issuedAt)
                .notBefore(issuedAt)
                .expiresAt(expiredAt)
                .audience(List.of("client"))
                .subject(authentication.getName())
                .claim("scope", Set.of("openid"))
                .claim("authorities", authorities).claim(
                        USER_ID,
                        ((Map<?, ?>) authentication.getDetails()).get(USER_ID)
                ).build();
    }

/*    @SuppressWarnings("unchecked")
    public OAuth2AccessToken createAccessToken(Authentication authentication, long expiresAtInMinutes) {
        JwtClaimsSet jwtClaimsSet = createJwtClaimsSet(authentication, expiresAtInMinutes);

        return new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue(),
                jwtClaimsSet.getIssuedAt(),
                jwtClaimsSet.getExpiresAt(),
                jwtClaimsSet.getClaim("scope")
        );
    }

    @SuppressWarnings("unchecked")
    public OAuth2RefreshToken createRefreshToken(Authentication authentication, long expiresAtInMinutes) {
        JwtClaimsSet jwtClaimsSet = createJwtClaimsSet(authentication, expiresAtInMinutes);

        return new OAuth2RefreshToken(
                jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue(),
                jwtClaimsSet.getIssuedAt(),
                jwtClaimsSet.getExpiresAt()
        );
    }*/

    @SuppressWarnings("unchecked")
    public OAuth2AccessToken createAccessToken(Authentication authentication, long expiresAtInMinutes) {
        Instant issuedAt = Instant.now();
        Instant expiredAt = issuedAt.plus(expiresAtInMinutes, ChronoUnit.MINUTES);
        Set<String> scopes =  Set.of("openid");

        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        JwtClaimsSet.Builder builder = JwtClaimsSet.builder()
                .issuer(AUTH_SERVICE_URL)
                .issuedAt(issuedAt)
                .notBefore(issuedAt)
                .expiresAt(expiredAt)
                .audience(List.of("client"))
                .subject(authentication.getName())
                .claim("scope", scopes)
                .claim("authorities", authorities);

        Map<String, String> clientDetails = (Map<String, String>) authentication.getDetails();

        JwtClaimsSet jwtClaimsSet = clientDetails.containsKey(USER_ID)
                ? builder.claim(USER_ID, clientDetails.get(USER_ID)).build()
                : builder.claim(SELLER_ID, clientDetails.get(SELLER_ID)).build();

        String tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        return new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, tokenValue, issuedAt, expiredAt, scopes);
    }

    @SuppressWarnings("unchecked")
    public OAuth2RefreshToken createRefreshToken(Authentication authentication, long expiresAtInMinutes) {
        Instant issuedAt = Instant.now();
        Instant expiredAt = issuedAt.plus(expiresAtInMinutes, ChronoUnit.MINUTES);
        Set<String> scopes =  Set.of("openid");

        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        JwtClaimsSet.Builder builder = JwtClaimsSet.builder()
                .issuer(AUTH_SERVICE_URL)
                .issuedAt(issuedAt)
                .notBefore(issuedAt)
                .expiresAt(expiredAt)
                .audience(List.of("client"))
                .subject(authentication.getName())
                .claim("scope", scopes)
                .claim("authorities", authorities);

        Map<String, String> clientDetails = (Map<String, String>) authentication.getDetails();

        JwtClaimsSet jwtClaimsSet = clientDetails.containsKey(USER_ID)
                ? builder.claim(USER_ID, clientDetails.get(USER_ID)).build()
                : builder.claim(SELLER_ID, clientDetails.get(SELLER_ID)).build();

        String tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        return new OAuth2RefreshToken(tokenValue, issuedAt, expiredAt);
    }

    public String parseToken(String token) {
        try {
            SignedJWT decodedJWT = SignedJWT.parse(token);
            return decodedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}