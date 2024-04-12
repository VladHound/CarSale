package car.sale.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserToSellerResponse(
        UUID userId,

        String email,
        String password,

        String firstName,
        String lastName,
        String patronymic,
        String photoId
) {
}