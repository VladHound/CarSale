package car.sale.userservice.dto.request;

public record UserRegistrationDto(
        UserCredentialsRequest credentials,
        String email,
        String password
) {
}