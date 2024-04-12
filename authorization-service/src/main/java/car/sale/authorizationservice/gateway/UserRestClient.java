package car.sale.authorizationservice.gateway;

import car.sale.auth.dto.ClientAuthDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface UserRestClient {

    @GetExchange(url = "/users/auth")
    ClientAuthDetails receiveUserAuthDetails(@RequestParam String email);
}
