package car.sale.authorizationservice.gateway;

import car.sale.auth.dto.ClientAuthDetails;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;


public interface SellerAuthClient {

    @GetExchange(url = "/sellers/auth")
    ClientAuthDetails receiveSellerAuthDetails(@RequestParam String email);
}
