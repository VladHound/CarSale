package car.sale.authorizationservice.service;

import car.sale.auth.dto.ClientAuthDetails;
import car.sale.authorizationservice.dto.AuthClientDetails;
import car.sale.authorizationservice.gateway.UserRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRestClient authClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ClientAuthDetails clientAuthDetails = authClient.receiveUserAuthDetails(email);

        return new AuthClientDetails(
                clientAuthDetails.email(),
                clientAuthDetails.password(),
                Set.of(new SimpleGrantedAuthority("ROLE_".concat("USER"))),
                clientAuthDetails.uuid().toString()
        );
    }
}