package car.sale.authorizationservice.service.provider;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import car.sale.authorizationservice.dto.AuthClientDetails;
import car.sale.authorizationservice.service.CustomUserDetailsService;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService userDetailsService;

    private final AuthenticationManagerBuilder auth;

    @PostConstruct
    public void postConstruct() {
        auth.authenticationProvider(this);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        AuthClientDetails clientDetails = (AuthClientDetails) userDetailsService.loadUserByUsername(email);

        if (password.equals(clientDetails.getPassword())) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    email, password, clientDetails.getAuthorities()
            );

            token.setDetails(Map.of("user-id", clientDetails.getExternalId()));
            return token;
        }

        throw new BadCredentialsException("Bad credentials for email: " + email);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}