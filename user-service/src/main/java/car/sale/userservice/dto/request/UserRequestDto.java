package car.sale.userservice.dto.request;

public record UserRequestDto(
        String firstName,
        String lastName,
        String email,
        String photoId
) {
}